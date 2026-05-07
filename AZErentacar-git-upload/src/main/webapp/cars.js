const params = new URLSearchParams(window.location.search);
const isGuest = params.get("guest") === "true";

function bookCar(carName) {
    if (isGuest) {
        alert("Rezervasyon yapabilmek için üye girişi yapmalısınız.");
    } else {
        window.location.href = "booking.xhtml?car=" + encodeURIComponent(carName);
    }
}

function isPlaceholder(value, placeholder) {
    return !value || value === placeholder;
}

function priceMatches(price, selectedRange) {
    if (isPlaceholder(selectedRange, "Fiyat Aralığı")) {
        return true;
    }

    if (selectedRange === "$50 - $80") {
        return price >= 50 && price <= 80;
    }

    if (selectedRange === "$80 - $120") {
        return price >= 80 && price <= 120;
    }

    if (selectedRange === "$120+") {
        return price >= 120;
    }

    return true;
}

function applyFilters() {
    const selectedLocation = document.getElementById("locationFilter").value;
    const selectedType = document.getElementById("typeFilter").value;
    const selectedPrice = document.getElementById("priceFilter").value;
    const pickupDate = document.getElementById("pickupDateFilter").value;
    const returnDate = document.getElementById("returnDateFilter").value;
    const rows = document.querySelectorAll(".car-list .car-row");
    const emptyMessage = document.getElementById("emptyFilterMessage");

    if (pickupDate && returnDate && returnDate < pickupDate) {
        alert("İade tarihi teslim alma tarihinden önce olamaz.");
        return;
    }

    let visibleCount = 0;

    rows.forEach(row => {
        const rowLocation = row.dataset.location;
        const rowType = row.dataset.type;
        const rowPrice = Number(row.dataset.price);

        const matchesLocation =
            isPlaceholder(selectedLocation, "Teslim Alma Konumu") || rowLocation === selectedLocation;
        const matchesType =
            isPlaceholder(selectedType, "Araç Tipi") || rowType === selectedType;
        const matchesPrice = priceMatches(rowPrice, selectedPrice);
        const isVisible = matchesLocation && matchesType && matchesPrice;

        row.style.display = isVisible ? "" : "none";

        if (isVisible) {
            visibleCount += 1;
        }
    });

    emptyMessage.style.display = visibleCount === 0 ? "block" : "none";
}

document.addEventListener("DOMContentLoaded", () => {
    const filterButton = document.getElementById("filterButton");

    if (filterButton) {
        filterButton.addEventListener("click", applyFilters);
    }
});
