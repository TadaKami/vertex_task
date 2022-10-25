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
        event.preventDefault();
        let msg = $("#sumNumbers").val();
        if (msg.length > 0) {
            eb.publish("sum.numbers",msg);
        }
    })
})