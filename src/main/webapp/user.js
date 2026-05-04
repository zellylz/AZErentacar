let isMember = false;
let selectedCar = null;

const cars = [
    {
        id: 1,
        name: "BMW 3 Series",
        price: "€95/day",
        status: "available",
        image: "image/bmw-3-series.png"
    },
    {
        id: 2,
        name: "Audi A4",
        price: "€110/day",
        status: "available",
        image: "image/audi-a4.png"
    },
    {
        id: 3,
        name: "Toyota Corolla",
        price: "€70/day",
        status: "available",
        image: "image/toyota-corolla.png"
    },
    {
        id: 4,
        name: "Volkswagen Golf",
        price: "€75/day",
        status: "busy",
        image: "image/volkswagen-golf.png"
    },
    {
        id: 5,
        name: "Renault Clio",
        price: "€60/day",
        status: "available",
        image: "image/renault-clio.png"
    },
    {
        id: 6,
        name: "Audi 2",
        price: "€100/day",
        status: "busy",
        image: "image/audi-2.png"
    }
];

function login() {
    localStorage.setItem("isMember", "true");
    window.location.href = "car.html";
}

function register() {
    localStorage.setItem("isMember", "true");
    window.location.href = "car.html";
}

function guestLogin() {
    localStorage.setItem("isMember", "false");
    window.location.href = "car.html";
}


function loadCars() {
    const carsList = document.getElementById("carsList");
    carsList.innerHTML = "";

    cars.forEach(car => {
        carsList.innerHTML += `
            <div class="car-card" onclick="openCarModal(${car.id})">
                <img src="${car.image}" alt="${car.name}">
                <div class="car-info">
                    <h3>${car.name}</h3>
                    <p>${car.price}</p>
                    <p class="${car.status === "available" ? "available" : "busy"}">
                        ${car.status === "available" ? "Müsait" : "Kirada"}
                    </p>
                </div>
            </div>
        `;
    });
}

function openCarModal(carId) {
    selectedCar = cars.find(car => car.id === carId);

    document.getElementById("modalCarImage").src = selectedCar.image;
    document.getElementById("modalCarName").innerText = selectedCar.name;
    document.getElementById("modalCarPrice").innerText = selectedCar.price;

    document.getElementById("modalCarStatus").innerText =
        selectedCar.status === "available" ? "Durum: Müsait" : "Durum: Kirada";

    document.getElementById("rentBtn").disabled = selectedCar.status !== "available";
    document.getElementById("rentBtn").innerText =
        selectedCar.status === "available" ? "Kirala" : "Bu araç müsait değil";

    if (isMember) {
        document.getElementById("memberFeatures").classList.remove("hidden");
        document.getElementById("guestWarning").classList.add("hidden");
    } else {
        document.getElementById("memberFeatures").classList.add("hidden");
        document.getElementById("guestWarning").classList.remove("hidden");
    }

    document.getElementById("carModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("carModal").classList.add("hidden");
}

function rentCar() {
    if (selectedCar.status !== "available") {
        alert("Bu araç şu anda müsait değil.");
        return;
    }

    alert(selectedCar.name + " başarıyla seçildi.");
}

function showMap() {
    window.open("https://www.google.com/maps/dir/?api=1&destination=RentaCar", "_blank");
}

function addComment() {
    const comment = document.getElementById("commentInput").value;
    const rating = document.getElementById("ratingInput").value;

    if (comment.trim() === "") {
        alert("Yorum boş olamaz.");
        return;
    }

    document.getElementById("comments").innerHTML += `
        <div class="comment">
            <strong>${rating}/5</strong>
            <p>${comment}</p>
        </div>
    `;

    document.getElementById("commentInput").value = "";
}
function openRegisterBox() {
    document.getElementById("loginBox").classList.add("hidden");
    document.getElementById("registerBox").classList.remove("hidden");
}

function openLoginBox() {
    document.getElementById("registerBox").classList.add("hidden");
    document.getElementById("loginBox").classList.remove("hidden");
}

function scrollToLogin() {
    document.getElementById("loginBox").scrollIntoView({ behavior: "smooth" });
}
function login() {
    window.location.href = "./cars.html";
}

function guestLogin() {
    window.location.href = "./cars.html?guest=true";
}

function goCarsPage() {
    window.location.href = "./cars.html";
}