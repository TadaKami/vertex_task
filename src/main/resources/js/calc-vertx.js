let eb = new EventBus();

function eventLogsToClient(err,msg) {
    let event = JSON.parse(msg.body);
    if(event.type == "publish"){
        // тут в историю добавляем сообщение
    }
}

eb.onopen = function (){
    eb.registerHandler("sum.numbers.logs",eventLogsToClient);
}

$(document).ready(function (){
    $("#calculation").submit(function(event){
        console.log("event",event);
        event.preventDefault();
        let msg = $("#sumNumbers").val();
        console.log("MSG BOY",msg);
        if (msg.length > 0) {
            console.log("msg",msg);
            eb.send("sum.numbers",msg);
        }
    })
})