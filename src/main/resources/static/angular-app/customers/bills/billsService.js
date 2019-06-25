app.service('billsService', ['$http', 'userService', function($http, userService){

    this.bills = [];
    var years = [2018, 2019];
    var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];

    this.billsData = [];





    this.orderBillsByDate = function(bills){
        bills = bills.sort(function (a, b) {
            var dateAParts = a.emitedDate.split("/");
            var dateBParts = b.emitedDate.split("/");
            return  (new Date(+dateAParts[2], dateAParts[1] - 1, +dateAParts[0]) < new Date(+dateBParts[2], dateBParts[1] - 1, +dateBParts[0])) ? 1 : ((new Date(+dateBParts[2], dateBParts[1] - 1, +dateBParts[0]) < new Date(+dateAParts[2], dateAParts[1] - 1, +dateAParts[0]))? -1 : 0)
        });

        return bills;
    }


    this.orderData = function(){

        this.billsData = {
                            "emitted"   :   { "bills": [] , "groups": [] , "yearTotal" : 0},
                            "received"  :   { "bills": [] , "groups": [] , "yearTotal" : 0}
                         };

        //this.printBills(this.bills);

        this.bills = this.orderBillsByDate(this.bills);

        //this.printBills(this.bills);

        this.bills.forEach(function(bill){
           bill.total = Number(bill.total.replace('$','').replace(',',''));
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
                totalEmitted += Number( item.total );
            });


            //filters received bills by month
            var receivedBillsByMonth = receivedBills.filter(bill => {
              return bill.emitedDate.split('/')[1] === month;
            });
            //calculates the totalReceived
            receivedBillsByMonth.forEach(function(item){
                totalReceived += Number( item.total );
            });


            var combined = emittedBillsByMonth.reduce((hash, obj) => {
               return obj.receiverName in hash ? hash[obj.receiverName].push(obj) : hash[obj.receiverName] = [obj], hash;
            }, Object.create(null));

            var combinedReceived = receivedBillsByMonth.reduce((hash, obj) => {
               return obj.emisorName in hash ? hash[obj.emisorName].push(obj) : hash[obj.emisorName] = [obj], hash;
            }, Object.create(null));

            var result = Object.values(combined);
            var groups = [];
            result.forEach(function(element){
                groups.push({"name": element[0].receiverName, "bills": element, "total": element.sum("total")});
            });

            var resultReceived = Object.values(combinedReceived);
            var groupsReceived = [];
            resultReceived.forEach(function(element){
                groupsReceived.push({"name": element[0].emisorName, "bills": element, "total": element.sum("total")});
            });


            this.billsData.emitted.bills.push({ "month": month, "bills": emittedBillsByMonth, "total": {"emitted" : totalEmitted, "received": totalReceived}, "groups": groups });
            this.billsData.received.bills.push({ "month": month, "bills": receivedBillsByMonth, "total": {"emitted" : totalEmitted, "received": totalReceived }, "groups" : groupsReceived });

        });

        var yearTotalEmitted = 0;
        var yearTotalReceived = 0;
        this.billsData.emitted.bills.forEach(function(bill){
                yearTotalEmitted += bill.total.emitted;
        });

        this.billsData.received.bills.forEach(function(bill){
                yearTotalReceived += bill.total.received;
        });

        this.billsData.emitted.yearTotal = yearTotalEmitted;
        this.billsData.received.yearTotal = yearTotalReceived;

        return this.billsData;

    }//end of function


    this.printBills = function(bills){
        var count = 1;
        bills.forEach(function(bill){
            console.log(count++ + " - " + bill.emitedDate);
        });
    }


    Array.prototype.sum = function (prop) {
        var total = 0
        for ( var i = 0, _len = this.length; i < _len; i++ ) {
            total += Number(this[i][prop])
        }
        return total
    }


}]);