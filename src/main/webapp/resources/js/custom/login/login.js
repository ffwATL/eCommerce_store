/*<![CDATA[*/
$(function(){
    var lg = $('#login');
    var magic =/*[[@{context:}]]*/'';
    var login = $('#btn');
    var username = $('#username');
    var password = $('#password');
    var uInVisible = $('.mail');
    var c_danger = 'c-danger';
    var c_info = 'c-info';
    var uVisible = uInVisible.find('.visible');
    uInVisible = uInVisible.find('.invisible');
    var pInVisible = $('.lock');
    var pVisible = pInVisible.find('.visible');
    pInVisible = pInVisible.find('.invisible');

    login.click(function(){
        var data = {
            _csrf: lg.find('input').val(),
            username: username.val(),
            password: password.val()
        };
        if(!inputRoutine()) resolveErrorMessage(0);
        else $.ajax({
            url: magic + "login",
            type: "POST",
            data: data,
            success: function(result){
                $('#home').click();
            },
            error: function(data){
                console.log(data.status);
                resolveErrorMessage(data.status)
            }
        });
    });
    function resolveErrorMessage (status){
        switch (status){
            case 401: {
                writeMessage("Invalid username or password :(", c_danger);
                break;
            }
            case 404: {
                writeMessage("This form has expired", c_info);
                break;
            }
            case 0: {
                writeMessage("Username or password are too short", c_danger);
                break;
            }
        }
    }
    function writeMessage (data, clazz){
        var msg = $('.message');
        msg.removeClass(c_danger);
        msg.removeClass(c_info);
        msg.addClass(clazz);
        msg = msg.find('p');
        msg.text(data);
        msg.fadeIn(200);
    }
    function paintInput(input, visible, invisible, correct){
        if(correct){
            input.css({'border-bottom':'solid .1em #e7e7e7', background: 'white', color: '#727272'});
            invisible.hide();
            visible.show();
            return true;
        }else{
            input.css({'border-bottom':'solid .1em #F25D75', background: '#FCE8EC', color: '#cc6e81'});
            visible.hide();
            invisible.show();
            return false;
        }
    }
    function checkInputLength(input, min){
        return input.val().length >= min;
    }
    function isEmail(input_email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(input_email.val());
    }
    function inputRoutine(){
        var enabled1 = isEmail(username);
        paintInput(username, uVisible, uInVisible, enabled1);
        var enabled = checkInputLength(password, 5);
        paintInput(password, pVisible, pInVisible, enabled);
        login.prop('disabled', !(enabled && enabled1));
        return (enabled && enabled1);
    }
    setTimeout(function(){
        username.bind('input propertychange)', function(){
            inputRoutine();
        });
        password.bind('input propertychange)', function(){
            inputRoutine();
        });
    },100);
});
/*]]>*/