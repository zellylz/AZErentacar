const customers = {
    "John Doe - BMW 3 Series": {
        name: "John Doe",
        phone: "+90 555 123 45 67",
        email: "john.doe@gmail.com",
        currentCar: "BMW 3 Series",
        rentalDate: "Apr 29, 2026",
        previousCars: "Audi A4, Toyota Corolla",
        accidentHistory: "Yok",
        licenseNo: "TR-458921",
        paymentStatus: "Ödendi"
    },

    "John Doe - RMV 3 Series": {
        name: "John Doe",
        phone: "+90 555 123 45 67",
        email: "john.doe@gmail.com",
        currentCar: "RMV 3 Series",
        rentalDate: "Apr 30, 2026",
        previousCars: "BMW 3 Series, Audi A4",
        accidentHistory: "1 küçük hasar kaydı",
        licenseNo: "TR-458921",
        paymentStatus: "Beklemede"
    },

    "Emily Stone - Audi A4": {
        name: "Emily Stone",
        phone: "+90 532 456 78 90",
        email: "emily.stone@gmail.com",
        currentCar: "Audi A4",
        rentalDate: "May 01, 2026",
        previousCars: "Mercedes EQA",
        accidentHistory: "Yok",
        licenseNo: "TR-771245",
        paymentStatus: "Ödendi"
    },

    "Michael Brown - Toyota Corolla": {
        name: "Michael Brown",
        phone: "+90 544 987 65 43",
        email: "michael.brown@gmail.com",
        currentCar: "Toyota Corolla",
        rentalDate: "May 02, 2026",
        previousCars: "Volkswagen Golf, Renault Clio",
        accidentHistory: "Yok",
        licenseNo: "TR-982314",
        paymentStatus: "Ödendi"
    },

    "David Miller - Mercedes EQA": {
        name: "David Miller",
        phone: "+90 530 222 11 00",
        email: "david.miller@gmail.com",
        currentCar: "Mercedes EQA",
        rentalDate: "May 02, 2026",
        previousCars: "BMW 3 Series",
        accidentHistory: "2 hasar kaydı",
        licenseNo: "TR-635812",
        paymentStatus: "Beklemede"
    },

    "Sophia Clark - Audi A4": {
        name: "Sophia Clark",
        phone: "+90 551 888 77 66",
        email: "sophia.clark@gmail.com",
        currentCar: "Audi A4",
        rentalDate: "May 04, 2026",
        previousCars: "Toyota Corolla",
        accidentHistory: "Yok",
        licenseNo: "TR-129845",
        paymentStatus: "Ödendi"
    }
};

const modal = document.createElement("div");
modal.className = "modal-overlay";
document.body.appendChild(modal);

document.querySelectorAll(".rental-row").forEach(row => {
    row.addEventListener("click", () => {
        const rentalText = row.querySelector("span").textContent.trim();
        const customer = customers[rentalText];

        if (!customer) return;

        modal.innerHTML = `
            <div class="customer-card">
                <button class="close-modal">&times;</button>

                <div class="customer-header">
                    <div class="customer-avatar">👤</div>
                    <div class="customer-info">
                        <h2>${customer.name}</h2>
                        <p>${customer.email}</p>
                    </div>
                </div>

                <div class="customer-detail">
                    <div class="detail-box">
                        <small>Telefon</small>
                        <strong>${customer.phone}</strong>
                    </div>

                    <div class="detail-box">
                        <small>Ehliyet No</small>
                        <strong>${customer.licenseNo}</strong>
                    </div>

                    <div class="detail-box">
                        <small>Şu An İstediği Araç</small>
                        <strong>${customer.currentCar}</strong>
                    </div>

                    <div class="detail-box">
                        <small>Kiralama Tarihi</small>
                        <strong>${customer.rentalDate}</strong>
                    </div>

                    <div class="detail-box full-box">
                        <small>Daha Önce Kiraladığı Araçlar</small>
                        <strong>${customer.previousCars}</strong>
                    </div>

                    <div class="detail-box">
                        <small>Kaza / Hasar Geçmişi</small>
                        <strong>${customer.accidentHistory}</strong>
                    </div>

                    <div class="detail-box">
                        <small>Ödeme Durumu</small>
                        <strong>${customer.paymentStatus}</strong>
                    </div>
                </div>
            </div>
        `;

        modal.classList.add("active");

        document.querySelector(".close-modal").addEventListener("click", closeModal);
    });
});

modal.addEventListener("click", (e) => {
    if (e.target === modal) {
        closeModal();
    }
});

function closeModal() {
    modal.classList.remove("active");
}
const menuItems = document.querySelectorAll(".menu a");
const mainContent = document.querySelector(".main-content");

const dashboardTitle = mainContent.querySelector("h1");

const dashboardSections = [
    document.querySelector(".stats-grid"),
    document.querySelector(".middle-grid"),
    document.querySelector(".bottom-grid")
];

const reservationsPage = document.querySelector(".reservations-page");
const customersPage = document.querySelector(".customers-page");
const reportsPage = document.querySelector(".reports-page");
const vehiclesPage = document.querySelector(".vehicles-page");

menuItems.forEach(item => {
    item.addEventListener("click", () => {
        menuItems.forEach(menu => menu.classList.remove("active"));
        item.classList.add("active");

        const menuText = item.textContent.trim();

        dashboardTitle.style.display = "none";
        dashboardSections.forEach(section => {
            section.style.display = "none";
        });

        reservationsPage.classList.remove("active-page");
        vehiclesPage.classList.remove("active-page");
        customersPage.classList.remove("active-page");
        reportsPage.classList.remove("active-page");
        if (menuText === "Dashboard") {
            dashboardTitle.style.display = "block";

            dashboardSections.forEach(section => {
                section.style.display = "";
            });
        }

        if (menuText === "Reservations") {
            reservationsPage.classList.add("active-page");
        }

        if (menuText === "Customers") {
            customersPage.classList.add("active-page");
        }
        if (menuText === "Reports") {
    reportsPage.classList.add("active-page");
}
        if (menuText === "Vehicles") {
    vehiclesPage.classList.add("active-page");
}
    });
});