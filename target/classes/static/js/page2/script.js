console.log("page2");

function fetchMessage() {
    fetch("/hello")
        .then((response) => response.text())
        .then((message) => {
            document.getElementById("message").textContent = message;
        });
}

const btn = document.querySelector("#elButton");
btn.addEventListener("click", () => {
    fetchMessage();
});
