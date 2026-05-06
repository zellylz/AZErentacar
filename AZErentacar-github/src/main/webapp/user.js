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

function guestLogin() {
    window.location.href = "cars.xhtml?guest=true";
}

function goCarsPage() {
    window.location.href = "cars.xhtml";
}
