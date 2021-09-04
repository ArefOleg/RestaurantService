window.onload =
    function myFunction(){document.querySelectorAll('.btn-success').forEach(function(el) {
        var dtHour = 11;
        var sdtMinute = 0;
        var dtSecond = 0;
        var dtTimeObject = new Date();
        dtTimeObject.setHours(dtHour, sdtMinute, dtSecond);

        var presentTime = new Date();
        if (presentTime > dtTimeObject){
            el.style.display = 'none';
            alert('You can`t vote after 11am!');
        }

    });}
