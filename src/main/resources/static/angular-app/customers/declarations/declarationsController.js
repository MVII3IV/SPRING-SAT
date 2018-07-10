angular.module('app').controller("declarationsController", ['$scope', '$http', function ($scope, $http) {
    $scope.title = "Declaraciones";




    $scope.months = [
        {
            name: "Enero",
            class: "month"
        },
        {
            name: "Febrero",
            class: "month"
        },
        {
            name: "Marzo",
            class: "month"
        },
        {
            name: "Abril",
            class: "disabled"
        },
        {
            name: "Mayo",
            class: "disabled"
        },
        {
            name: "Junio",
            class: "disabled"
        },
        {
            name: "Julio",
            class: "disabled"
        },
        {
            name: "Agosto",
            class: "disabled"
        },
        {
            name: "Septiembre",
            class: "disabled"
        },
        {
            name: "Octubre",
            class: "disabled"
        },
        {
            name: "Noviembre",
            class: "disabled"
        },
        {
            name: "Diciembre",
            class: "disabled"
        }
    ]




    $scope.openDeclaration = function(month){
        if(month.class == 'disabled')
            return

        $scope.selectedMonth = month.name;
        $('#modal_declaration').modal('show');
    }





    // If absolute URL from the remote server is provided, configure the CORS
    // header on that server.
    var url = '/js/plugins/pdfjs/test.pdf';
    var pdfDoc = {};

    // Loaded via <script> tag, create shortcut to access PDF.js exports.
    var pdfjs = window['pdfjs-dist/build/pdf'];

    // The workerSrc property shall be specified.
    pdfjs.GlobalWorkerOptions = {};
    pdfjs.GlobalWorkerOptions.workerSrc = '/js/plugins/pdfjs/pdf.worker.js';

    // Asynchronous download of PDF
    var loadingTask = pdfjs.getDocument(url);
    loadingTask.promise.then(function(pdf) {
      console.log('PDF loaded');

      // Fetch the first page
      var pageNumber = 1;
      pdfDoc = pdf;

      pdf.getPage(pageNumber).then(function(page) {
        console.log('Page loaded');

        var scale = 1.5;
        var viewport = page.getViewport(scale);

        // Prepare canvas using PDF page dimensions
        var canvas = document.getElementById('pdf-canvas');
        var context = canvas.getContext('2d');
        canvas.height = viewport.height;
        canvas.width = viewport.width;

        // Render PDF page into canvas context
        var renderContext = {
          canvasContext: context,
          viewport: viewport
        };
        var renderTask = page.render(renderContext);
        renderTask.then(function () {
          console.log('Page rendered');
        });
      });
    }, function (reason) {
      // PDF loading error
      console.error(reason);
    });


    $scope.changePage = function(){
        pdfDoc.getPage(2).then(function(page) {
            console.log('Page loaded');

            var scale = 1.5;
            var viewport = page.getViewport(scale);

            // Prepare canvas using PDF page dimensions
            var canvas = document.getElementById('pdf-canvas');
            var context = canvas.getContext('2d');
            canvas.height = viewport.height;
            canvas.width = viewport.width;

            // Render PDF page into canvas context
            var renderContext = {
              canvasContext: context,
              viewport: viewport
            };
            var renderTask = page.render(renderContext);
            renderTask.then(function () {
              console.log('Page rendered');
            });
          });
    }

}]);