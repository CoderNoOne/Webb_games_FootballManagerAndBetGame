let counter = 0;

const getColor = () => {
    let arr = ['red', 'yellow', 'green', 'blue'];
    counter = counter >= arr.length ? 0 : counter;
    return arr[counter++];
};
setInterval(() => {
    let date = new Date();
    let elementById = document.getElementById("c_t");


    elementById.innerHTML = ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2) + ":" + ("0" + date.getSeconds()).slice(-2);
    elementById.style.color = getColor();
}, 1000);

