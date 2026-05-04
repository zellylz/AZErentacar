const params = new URLSearchParams(window.location.search);
const isGuest = params.get("guest") === "true";

function bookCar(carName) {
    if (isGuest) {
        alert(carName + " için rezervasyon yapabilmek için üye girişi yapmalısın.");
    } else {
        window.location.href = "booking.html?car=" + encodeURIComponent(carName);
    }
}