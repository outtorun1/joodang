package com.joodang.cart.controller;

import com.joodang.cart.dto.CartDetailDto;
import com.joodang.cart.dto.CartOrderDto;
import com.joodang.cart.dto.CartProductDto;
import com.joodang.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartProductDto cartProductDto,
                                              BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage()) ;
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName() ;
        Long cartProductId = 0L ;

        try{
            cartProductId = cartService.addCart(cartProductDto, email);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    // header.html 문서의 '장바구니' 메뉴를 클릭하면 여기로 옵니다.
    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model){
        String email = principal.getName() ;
        List<CartDetailDto> cartDetailDtoList = cartService.getCartList(email) ;

        // View에서 참조 가능하도록 해당 Model(데이터)를 request에 바인딩합니다.
        model.addAttribute("cartProducts", cartDetailDtoList) ;

        return "cart/cartList" ;
    }


    // @PatchMapping 어노테이션은 요청된 자원의 일부(여기서는 상품 수량)를 업데이트할 때 사용합니다.
    @PatchMapping("/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity updateCartProduct(
            @PathVariable("cartProductId") Long cartProductId, int count, Principal principal
    ){
        System.out.println("count : "+ count);

        if(count <= 0){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST) ;

        }else if(!cartService.validateCartProduct(cartProductId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN) ;
        }
        cartService.updateCartProductCount(cartProductId, count);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK) ;
    }


    // 장바구니의 특정 상품에 대하여 x 버튼 클릭시 호출되는 메소드입니다.
    // @DeleteMapping 어노테이션은 http의 DELETE에 사용되는 어노테이션입니다.
    @DeleteMapping("/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity deleteCartProduct(
            @PathVariable("cartProductId") Long cartProductId, Principal principal
    ){
        if(!cartService.validateCartProduct(cartProductId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN) ;
        }
        cartService.deleteCartProduct(cartProductId);

        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK) ;
    }

    // html 문서에서 "주문하기" 버튼을 클릭하였습니다.
    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartProduct(
            @RequestBody CartOrderDto cartOrderDto,
            Principal principal
    ){

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList() ;

        // 주문 상품을 선택하지 않았는지 체크합니다.
        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문한 상품을 선택해 주세요.", HttpStatus.FORBIDDEN) ;
        }

        String email = principal.getName() ;

        for(CartOrderDto cartOrder :cartOrderDtoList){
            Long cartProductId = cartOrder.getCartProductId() ;
            if(!cartService.validateCartProduct(cartProductId, email)){ // 주문 가능한지 권한 체크
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN) ;
            }
        }

        // 주문 로직을 호출하고, 주문 번호를 반환 받습니다.
        Long orderId = cartService.orderCartProduct(cartOrderDtoList, email);

        // 주문 번호와 함께 리턴하기
        return new ResponseEntity<Long>(orderId, HttpStatus.OK) ;
    }
}