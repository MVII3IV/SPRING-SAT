app.service('billsService', ['$http', 'userService', function($http, userService){

    this.bills = [];
    var years = [2018, 2019];
    var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];

    this.billsData = [];

    this.orderData = function(billss){

        this.billsData = {
                            "emitted"   :   { "bills": [] , "groups": [] },
                            "received"  :   { "bills": [] , "groups": [] }
                         };

        //this code orders bills by date
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

        //this code calculates the total by month for incomes and outcomes
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

            this.billsData.emitted.bills.push({ "month": month, "bills": emittedBillsByMonth, "total": {"emitted" : totalEmitted, "received": totalReceived } });
            this.billsData.received.bills.push({ "month": month, "bills": receivedBillsByMonth, "total": {"emitted" : totalEmitted, "received": totalReceived } });

        });


        var combined = emittedBills.reduce((hash, obj) => {
           return obj.receiverName in hash ? hash[obj.receiverName].push(obj) : hash[obj.receiverName] = [obj], hash;
        }, Object.create(null));

        //var result = Object.values(combined);
        this.billsData.emitted.groups = combined;


        return this.billsData;

    }//end of function





}]);