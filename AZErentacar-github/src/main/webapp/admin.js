const menuItems = document.querySelectorAll(".menu a");
const mainContent = document.querySelector(".main-content");
const dashboardTitle = mainContent.querySelector("h1");

const dashboardSections = [
    document.querySelector(".stats-grid"),
    document.querySelector(".middle-grid"),
    document.querySelector(".bottom-grid")
];

const pages = {
    reservations: document.querySelector(".reservations-page"),
    customers: document.querySelector(".customers-page"),
    reports: document.querySelector(".reports-page"),
    vehicles: document.querySelector(".vehicles-page")
};

function hideDashboard() {
    dashboardTitle.style.display = "none";
    dashboardSections.forEach(section => {
        section.style.display = "none";
    });
}

function showDashboard() {
    dashboardTitle.style.display = "block";
    dashboardSections.forEach(section => {
        section.style.display = "";
    });
}

function hidePages() {
    Object.values(pages).forEach(page => {
        if (page) {
            page.classList.remove("active-page");
        }
    });
}

function showAdminPage(targetPage, selectedItem) {
    if (!targetPage) {
        return;
    }

    menuItems.forEach(menu => menu.classList.remove("active"));
    if (selectedItem) {
        selectedItem.classList.add("active");
    }

    hidePages();

    if (targetPage === "panel") {
        showDashboard();
        return;
    }

    hideDashboard();

    const page = pages[targetPage];
    if (!page) {
        return;
    }

    page.classList.add("active-page");

    if (targetPage === "vehicles") {
        window.location.hash = "vehiclesSection";
        page.scrollIntoView({ behavior: "smooth", block: "start" });
    }
}

function createRentalModal() {
    const modal = document.createElement("div");
    modal.className = "modal-overlay";
    document.body.appendChild(modal);

    modal.addEventListener("click", event => {
        if (event.target === modal) {
            modal.classList.remove("active");
        }
    });

    return modal;
}

function showRentalDetails(row, modal) {
    const title = row.querySelector(".rental-details strong")?.textContent.trim() || "-";
    const contact = row.querySelector(".rental-details small")?.textContent.trim() || "-";
    const startDate = row.querySelector(":scope > small")?.textContent.trim() || "-";
    const [name, car] = title.split(" - ");
    const [email, phone] = contact.split(" · ");

    modal.innerHTML = `
        <div class="customer-card">
            <button class="close-modal">&#215;</button>

            <div class="customer-header">
                <div class="customer-avatar">👤</div>
                <div class="customer-info">
                    <h2>${name || "-"}</h2>
                    <p>${email || "-"}</p>
                </div>
            </div>

            <div class="customer-detail">
                <div class="detail-box">
                    <small>Telefon</small>
                    <strong>${phone || "-"}</strong>
                </div>

                <div class="detail-box">
                    <small>Araç</small>
                    <strong>${car || "-"}</strong>
                </div>

                <div class="detail-box">
                    <small>Başlangıç Tarihi</small>
                    <strong>${startDate}</strong>
                </div>

                <div class="detail-box">
                    <small>Durum</small>
                    <strong>Aktif</strong>
                </div>
            </div>
        </div>
    `;

    modal.classList.add("active");
    modal.querySelector(".close-modal").addEventListener("click", () => {
        modal.classList.remove("active");
    });
}

window.showAdminPage = showAdminPage;

menuItems.forEach(item => {
    item.addEventListener("click", () => {
        showAdminPage(item.dataset.page, item);
    });
});

const rentalModal = createRentalModal();
document.querySelectorAll(".rental-row").forEach(row => {
    row.addEventListener("click", () => showRentalDetails(row, rentalModal));
});
