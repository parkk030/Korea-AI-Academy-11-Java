function submitButtononOnClick(){
    const writter = document.querySelector(".writter");
    const date = document.querySelector(".date");
    const content = document.querySelector(".content");

    const writterValue = writter.value;
    const dateValue = date.value;
    const contentValue = content.value;


    const todoData = {
        "writter" : writterValue,
        "date" : dateValue,
        "content" : contentValue,
    }

    console.log(todoData);

    fetch("http://localhost:8080/api/todos", {
        method: "post" ,
        body: JSON.stringify(todoData),
        headers: {
            "Content-Type" : "application/json",
        }
    });


}

function main() {
    const submitButton = document.querySelector(".submit-button");


    submitButton.onclick = submitButtononOnClick;
}

main();