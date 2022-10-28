
function itializeEventBus(){
    let eb = new EventBus("http://localhost:8080/eventbus");

    function eventLogsToClient(err,msg) {
        let event = JSON.parse(msg.body);
        console.log("err",err);
        console.log("msg",msg);
        console.log("event",event);
        if(event.type == "publish"){
            let txtArea = document.getElementById("historySums");
            if (txtArea) {
                if (txtArea.value == "") {
                    txtArea.value = event.message;
                }else{
                    txtArea.value += "\n"+event.message;
                }
            }
        }
    }

    eb.onopen = () => {
        console.log("open");
        eb.registerHandler("sum.numbers.logs",eventLogsToClient);
    }
    eb.onreconnect = ()=> {
        console.log("reconnect");
    };
    return eb;
}


$(document).ready(function (){
    let eb = itializeEventBus();
    console.log("event bus init");
    $("#calculation").submit(function(event){
        let firstNum = document.getElementById("firstNum");
        let secondNum = document.getElementById("secondNum");
        console.log("event",event);
        event.preventDefault();
        let msg = "";

        console.log("firstNum",firstNum.value);
        console.log("secondNum",secondNum.value);
        if(firstNum.value != "" && secondNum.value != ""){
            msg = Number(firstNum.value) + Number(secondNum.value);
            eb.send("sum.numbers",msg);
        }else{
            console.log("Введите числа");
        }
    })
})