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

        var orderedByName = bills.sort(function (a, b) {
            var dateAParts = a.emitedDate.split("/");
            var dateBParts = b.emitedDate.split("/");
            return  (new Date(+dateAParts[2], dateAParts[1] - 1, +dateAParts[0]) < new Date(+dateBParts[2], dateBParts[1] - 1, +dateBParts[0])) ? 1 : ((new Date(+dateBParts[2], dateBParts[1] - 1, +dateBParts[0]) < new Date(+dateAParts[2], dateAParts[1] - 1, +dateAParts[0]))? -1 : 0)
        });


    }




}]);