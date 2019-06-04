app.service('billsService', ['$http', 'userService', function($http, userService){

    this.bills = [];
    var years = [2018, 2019];
    var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];

    this.billsData = [];
    /*{
        "emitted" : [],
        "received" : []
    }*/


    this.orderData = function(bills){

        this.billsData = {"emitted" : [], "received" : []};

        this.bills = this.bills.sort(function (a, b) {
            var dateAParts = a.emitedDate.split("/");
            var dateBParts = b.emitedDate.split("/");
            return  (new Date(+dateAParts[2], dateAParts[1] - 1, +dateAParts[0]) < new Date(+dateBParts[2], dateBParts[1] - 1, +dateBParts[0])) ? 1 : ((new Date(+dateBParts[2], dateBParts[1] - 1, +dateBParts[0]) < new Date(+dateAParts[2], dateAParts[1] - 1, +dateAParts[0]))? -1 : 0)
        });

        var emittedBills = this.bills.filter(bill => {
          return bill.emited === true;
        })

        var receivedBills = this.bills.filter(bill => {
          return bill.emited === false;
        })




        months.forEach(month => {

            var totalEmitted = 0;
            var totalReceived = 0;

            //filters emitted bills by month
            var emittedBillsByMonth = emittedBills.filter(bill => {
              return bill.emitedDate.split('/')[1] === month;
            });
            //calculates the totalEmitted
            emittedBillsByMonth.forEach(function(item){
                totalEmitted += Number( item.total.replace('$','').replace(',','') );
            });


            //filters received bills by month
            var receivedBillsByMonth = receivedBills.filter(bill => {
              return bill.emitedDate.split('/')[1] === month;
            });
            //calculates the totalReceived
            receivedBillsByMonth.forEach(function(item){
                totalReceived += Number( item.total.replace('$','').replace(',','') );
            });

            this.billsData.emitted.push({ "month": month, "bills": emittedBillsByMonth, "total": totalEmitted });
            this.billsData.received.push({ "month": month, "bills": receivedBillsByMonth, "total": totalReceived });

        });




    }//end of function




}]);