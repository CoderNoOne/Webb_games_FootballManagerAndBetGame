const x = setInterval(function () {

    let countDownDate = new Date(matchDate).getTime();

    let now = new Date();
    let utc = new Date(now.getTime() + now.getTimezoneOffset() * 60000);

    now = utc.getTime();
    let distance = countDownDate - now;

    let days = Math.floor(distance / (1000 * 60 * 60 * 24));
    let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    let seconds = Math.floor((distance % (1000 * 60)) / 1000);

    document.getElementById("demo").innerText = days + "d " + hours + "h " + minutes + "m " + seconds + "s ";

    if (distance < 0) {
        clearInterval(x);
        document.getElementById("demo").innerHTML = "MATCH HAS BEEN STARTED ALREADY";
    }
}, 1000);
