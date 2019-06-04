app.service('billsService', ['$http', 'userService', function($http, userService){

    var bills = [];
    var years = [2018, 2019];
    var months = [01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11, 12];


    var billsData =
    {
        "emitted" : [
          {
            "month": 0,
            "bills": {},
            "total":0
          }
        ],
        "received" : [
          {
            "month": 0,
            "bills": {},
            "total":0
          }
        ]
    }

    this.orderData = function(bills){
        bills = sortYear(bills);
        bills = sortMonth(bills);
        bills = sortDay(bills);
    }


    function sortYear(arr) {
        var len = arr.length;

        for (var i = 0; i < len ; i++) {
            for(var j = 0 ; j < len - i - 1; j++){ // this was missing
                if (  (parseInt(arr[j].emitedDate.split('/')[2]) > parseInt(arr[j + 1].emitedDate.split('/')[2])) ) {
                    // swap
                    var temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    function sortMonth(arr) {
        var len = arr.length;

        for (var i = 0; i < len ; i++) {
            for(var j = 0 ; j < len - i - 1; j++){ // this was missing
                if (  (parseInt(arr[j].emitedDate.split('/')[1]) > parseInt(arr[j + 1].emitedDate.split('/')[1])) &&  (parseInt(arr[j].emitedDate.split('/')[2]) == parseInt(arr[j + 1].emitedDate.split('/')[2]))  ) {
                    // swap
                    var temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    function sortDay(arr) {
        var len = arr.length;

        for (var i = 0; i < len ; i++) {
            for(var j = 0 ; j < len - i - 1; j++){ // this was missing
                if (  (parseInt(arr[j].emitedDate.split('/')[0]) > parseInt(arr[j + 1].emitedDate.split('/')[0])) &&  (parseInt(arr[j].emitedDate.split('/')[1]) == parseInt(arr[j + 1].emitedDate.split('/')[1])) ) {
                    // swap
                    var temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }


}]);