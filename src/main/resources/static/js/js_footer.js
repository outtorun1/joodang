$(document).ready(function() {

    <!--top 버튼 쿼리-->
    $(window).scroll(function() {
        if ($(this).scrollTop() > 0) {
            $('.btn_gotop').show();
        } else {
            $('.btn_gotop').hide();
        }
    });

    $('.btn_gotop').click(function() {
        $('html, body').animate({
            scrollTop : 0
        }, 50);
        return false;
    });
});