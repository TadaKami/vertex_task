$(document).ready(function (){
    let eb = initializeEventBus();
    keyDownEnterSumm();
    $("#calculation").submit(function(event){
        event.preventDefault();

        let firstNum = document.getElementById("firstNum");
        let secondNum = document.getElementById("secondNum");
        let msg = "";

        if(firstNum.value != "" && secondNum.value != ""){
            msg = Number(firstNum.value) + Number(secondNum.value);
            eb.send("sum.numbers",msg);
        }else{
        }
    });
})

function keyDownEnterSumm(){
    document.addEventListener("keyup",(ev)=>{
       if(ev.keyCode === 13){
         let calcBtn = document.getElementById("#calculation");
           if (calcBtn) {
               calcBtn.click();
           }
       }
    });
}
function initializeEventBus(){
    let eb = new EventBus("http://localhost:8080/eventbus");

    function eventLogsToClient(err,msg) {
        let event = JSON.parse(msg.body);
        let event_msg = event.message;
        console.log("err",err);
        console.log("msg",msg);
        console.log("event",event);
        if(event.type == "publish"){
            let txtArea = $("#historySums");
            if (txtArea.text() != "") {
                event_msg = event_msg+"\n";
            }
            txtArea.prepend(event_msg);
        }
    }

    eb.onopen = () => {
        console.log("open");
        eb.registerHandler("sum.numbers.logs",eventLogsToClient);
    }
    return eb;
}