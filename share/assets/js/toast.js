/**
 * Created by donghoang on 03/02/2016.
 */
Toast = {
    DURATION_LONG: 6000,
    DURATION_SHORT: 3000,
    THEME_DARK: {
        color: 'white',
        background: 'rgba(0, 0, 0, 0.55)'
    },
    THEME_LIGHT: {
        color: 'black',
        background: 'rgba(255, 255, 255, 0.58)'
    },
    MakeToast: function (message, theme, duration) {
        message = (typeof message === 'undefined') ? 'Null Toast' : message;
        theme = (typeof theme === 'undefined') ? this.THEME_DARK : theme;
        duration = (typeof duration === 'undefined') ? this.DURATION_SHORT : duration;
        if (document.querySelector("#Toast-style") === null) {
            style = document.createElement('style');
            style.id = "Toast-style";
            style.innerHTML = ".visible-toast\
            {\
                visibility: visible;\
                opacity: 1;\
                transition: opacity " + duration + "ms linear;\
            }\
            /* Fade-Out Effect */\
            .hidden-toast\
            {\
                visibility: hidden;\
                opacity: 0;\
                transition: visibility 0s " + duration + "ms, opacity " + duration + "ms linear;\
            }";
            document.body.appendChild(style);
        }

        if (document.querySelector("#Toast") != null) {
            document.body.removeChild(document.querySelector("#Toast"));
        }
        div = document.createElement('div');
        div.id = "Toast";
        div.style.position = 'fixed';
        div.style.textAlign = 'center';
        div.style.bottom = "10%";
        div.style.width = "100%";
        div.style.zIndex = 99999999;
        div.className = "visible-toast";
        div.innerHTML = "<span id='message'" +
            "style='color: " + theme.color +
            "; background: " + theme.background +
            "; border-radius: 15px; max-width: 90%; margin: auto; padding: 10px 20px;'>" + message + "</span>";
        document.body.appendChild(div);
        setTimeout(function () {
            div.className = 'hidden-toast';
        }, 100);
        setTimeout(close, duration);
        function close() {
            if (document.querySelector("#Toast") != null) {
                document.body.removeChild(div);
            }
            if (document.querySelector("#Toast-style") != null) {
                document.body.removeChild(document.querySelector("#Toast-style"));
            }
        }
    }
}
