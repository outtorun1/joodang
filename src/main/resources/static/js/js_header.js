$(document).ready(function() {

    $('.dropdown-toggle-story').mouseover(function() {
        $('.dropdown-toggle-story').next('.dropdown-menu').show();
    });

    $('.dropdown-toggle-story').mouseout(function() {
        t = setTimeout(function() {
            $('.dropdown-toggle-story').next('.dropdown-menu').hide();
        }, 100);

        $('.dropdown-toggle-story')
            .next('.dropdown-menu').on('mouseenter', function() {
            $('.dropdown-toggle-story').next('.dropdown-menu').show();
            clearTimeout(t);
        })
            .on('mouseleave', function() {
                $('.dropdown-toggle-story').next('.dropdown-menu').hide();
            })
    });

    $('.dropdown-toggle-board').mouseover(function() {
        $('.dropdown-toggle-board').next(
            '.dropdown-menu').show();
    });



    $('.dropdown-toggle-product').mouseover(function() {
        console.log($('.dropdown-toggle-product').children('.dropdown-menu'));

        $('.dropdown-toggle-product').next('.dropdown-menu').show();
    });

    $('.dropdown-toggle-product').mouseout(function() {
        t = setTimeout(function() {
            $('.dropdown-toggle-product').next('.dropdown-menu').hide();
        }, 100);

        $('.dropdown-toggle-product').next('.dropdown-menu').on('mouseenter',function() {
            $('.dropdown-toggle-product').next('.dropdown-menu').show();
            clearTimeout(t);
        }).on('mouseleave',function() {
            $('.dropdown-toggle-product').next('.dropdown-menu').hide();
        })
    });

    $('.dropdown-toggle-commuity').mouseover(function() {
        $('.dropdown-toggle-commuity').next('.dropdown-menu').show();
    });

    $('.dropdown-toggle-commuity').mouseout(function() {
        t = setTimeout(function() {
            $('.dropdown-toggle-commuity').next('.dropdown-menu').hide();
        }, 100);

        $('.dropdown-toggle-commuity').next('.dropdown-menu').on('mouseenter',function() {
            $('.dropdown-toggle-commuity').next('.dropdown-menu').show();
            clearTimeout(t);
        }).on('mouseleave',function() {
            $('.dropdown-toggle-commuity').next('.dropdown-menu').hide();
        })
    });

    $('.dropdown-toggle-service').mouseover(function() {
        $('.dropdown-toggle-service').next('.dropdown-menu').show();
    });

    $('.dropdown-toggle-service').mouseout(function() {
        t = setTimeout(function() {
            $('.dropdown-toggle-service').next('.dropdown-menu').hide();
        }, 100);

        $('.dropdown-toggle-service').next('.dropdown-menu').on('mouseenter',function() {
            $('.dropdown-toggle-service').next('.dropdown-menu').show();
            clearTimeout(t);
        }).on('mouseleave', function() {
            $('.dropdown-toggle-service').next('.dropdown-menu').hide();
        })
    });
});