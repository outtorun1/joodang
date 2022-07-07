package com.joodang.cart.service;

import com.joodang.cart.dto.CartDetailDto;
import com.joodang.cart.dto.CartOrderDto;
import com.joodang.cart.dto.CartProductDto;
import com.joodang.cart.entity.Cart;
import com.joodang.cart.entity.CartProduct;
import com.joodang.cart.repository.CartProductRepository;
import com.joodang.cart.repository.CartRepository;
import com.joodang.member.entity.Member;
import com.joodang.member.repository.MemberRepository;
import com.joodang.order.dto.OrderDto;
import com.joodang.order.service.OrderService;
import com.joodang.product.entity.Product;
import com.joodang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

// @RequiredArgsConstructor를 작성해 두면, 생성자를 통하여 해당 변수에 값을 주입해줍니다.
// @Autowired는 주로 변수에 작성하는 데, setter을 이용하여 주입해 줍니다.
@Service
@RequiredArgsConstructor // 생성자(Constructor)를 통하여 필수 입력(Required)해야 하는 매개 변수(Args)
public class CartService {
    private final ProductRepository productRepository ;
    private final MemberRepository memberRepository ;
    private final CartRepository cartRepository ;
    private final CartProductRepository cartProductRepository ;

    // 장바구니에 담을 정보와 이메일을 이용하여 카트에 추가합니다.
    public Long addCart(CartProductDto cartProductDto, String email){
        Long productId = cartProductDto.getProductId() ;

        // 장바구니에 담고자 하는 상품
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new) ;

        // 로그인 한 사람의 정보
        Member member = memberRepository.findByEmail(email) ;

        // 로그인 한 사람의 장바구니 정보
        Cart cart = cartRepository.findByMemberEmail(member.getEmail()) ;

        if(cart == null){ // 장바구니가 준비 않된 경우
            cart = Cart.createCart(member) ;
            cartRepository.save(cart) ;
        }

        CartProduct savedCartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId()) ;

        if(savedCartProduct != null){ // 해당 상품이 나의 카트에 이미 들어 있는 경우
            // 이미 담긴 상품에 대하여 수량을 누적합니다.
            savedCartProduct.addCount(cartProductDto.getCount());
            return savedCartProduct.getId();

        }else{ // 신규 상품을 장바구니에 담고자 하는 경우
            CartProduct cartProduct = CartProduct.createCartProduct(cart, product, cartProductDto.getCount()) ;
            cartProductRepository.save(cartProduct) ;
            return cartProduct.getId();
        }
    }

    // 로그인 한 사람의 email 정보를 이용하여 당사자의 카트에 들어 있는 상품 목록을 조회합니다.
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        // email을 사용하여 해당 회원이 누구인지 파악합니다.
        Member member = memberRepository.findByEmail(email) ;

        // 로그인 한 사용자의 장바구니 정보를 읽어 들입니다.
        Cart cart = cartRepository.findByMemberEmail(member.getEmail()) ;

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>() ;

        if(cart == null){ // 카트 준비가 안 된 경우
            return cartDetailDtoList ; // return empty cartList
        }else{
            Long cartId = cart.getId() ;
            cartDetailDtoList = cartProductRepository.findCartDetailDtoList(cartId) ;
            return cartDetailDtoList ;
        }
    }

    // 로그인 한 회원과 장바구니 상품을 저장한 회원이 동일한지 체크하는 메소드
    @Transactional(readOnly = true)
    public boolean validateCartProduct(Long cartProductId, String email){
        // 이메일을 사용하여 로그인 한 회원의 정보를 취득합니다.
        Member curMember = memberRepository.findByEmail(email) ;

        // 카트 상품 아이디를 사용하여 CartProduct 정보를 취득합니다.
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);

        // 해당 Cart가 누구의 Cart인지 확인합니다.
        Member savedMember = cartProduct.getCart().getMember() ;

        // 동일한 사람이면 true를 반환해 줍니다.
        return StringUtils.equals(curMember.getEmail(), savedMember.getEmail()) ;
    }

    // 장바구니의 수량을 업데이트해주는 메소드입니다.
    public void updateCartProductCount(Long cartProductId, int count){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new) ;
        cartProduct.updateCount(count);
        cartProductRepository.save(cartProduct);
    }

    // 카트에 들어 있는 특정 상품의 id를 이용하여, 카트 목록에서 상품을 삭제합니다.
    public void deleteCartProduct(Long cartProductId){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        cartProductRepository.delete(cartProduct);
    }

    private final OrderService orderService ;

    public Long orderCartProduct(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>() ;
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartProduct cartProduct = cartProductRepository.findById(cartOrderDto.getCartProductId())
                    .orElseThrow(EntityNotFoundException::new);

            // OrderDto는 상품의 id와 수량 정보를 담고 있는 클래스입니다.
            OrderDto orderDto = new OrderDto();

            orderDto.setProductId(cartProduct.getProduct().getId());
            orderDto.setCount(cartProduct.getCount());

            orderDtoList.add(orderDto) ;
        }

        // 장바구니에 담은 상품들을 주문하기 위하여 주문 로직을 호출합니다.
        Long orderId = orderService.orders(orderDtoList, email);

        // 주문된 상품들은 장바구니 목록에서 제거 되어야 합니다.
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartProduct cartProduct = cartProductRepository.findById(cartOrderDto.getCartProductId())
                    .orElseThrow(EntityNotFoundException::new);
            cartProductRepository.delete(cartProduct);
        }
        return orderId ;
    }
}