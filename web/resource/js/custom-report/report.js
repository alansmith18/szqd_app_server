


function LineReport(canvasID,reportData)
{
    this.leftMargin = 50;
    this.bottomMargin = 50;
    this.canvasID = canvasID;
    this.reportArea = document.getElementById(this.canvasID);
    this.canvasWidth = this.reportArea.width;
    this.canvasHeight = this.reportArea.height;
    this.canvasContext = this.reportArea.getContext("2d");
    this.pointStartX = 0;
    this.pointStartY = 0;
    this.marginPointX = 0;
    this.numberOfPoint = reportData.length;
    this.reportData = reportData;
    this.reportWidth = 0;
    this.reportHeight = 0;

    this.topMarginForReportContent = 0;
    this.rightMarginForReportContent = 0;

    this.bottomMarginForDate = 2;

    //原点的半径
    this.pointRadius = 3;

    //报表的内容高度
    this.reportContentHeight = 0;

    //对于数据的单位刻度
    this.basicPointYForData = 0;

    //触发鼠标悬停事件的x轴间距
    this.onmouseoverEventAreaX = 1;


    this.initReport = function()
    {
        this.canvasContext.lineWidth = 0.1;
        this.reportWidth = this.canvasWidth - this.leftMargin * 2;
        this.reportHeight = this.canvasHeight - this.bottomMargin * 2;

        var reportStartX = this.leftMargin;
        var reportStartY = this.bottomMargin;
        var reportEndX = this.canvasWidth - this.leftMargin;
        var reportEndY = this.canvasHeight - this.bottomMargin;

        this.canvasContext.beginPath();
        this.canvasContext.moveTo(reportStartX,reportStartY);
        this.canvasContext.lineTo(reportStartX,reportEndY);
        this.canvasContext.stroke();

        this.canvasContext.beginPath();
        this.canvasContext.moveTo(reportStartX,reportEndY);
        this.canvasContext.lineTo(reportEndX,reportEndY);
        this.canvasContext.stroke();

        this.pointStartX = reportStartX;
        this.pointStartY = reportEndY;

        this.marginPointX = (this.reportWidth - this.rightMarginForReportContent) / (this.numberOfPoint - 1);

        this.reportContentHeight = (this.reportHeight - this.topMarginForReportContent);
        var maxData = 0;
        for(var i = 0; i < this.reportData.length; i++)
        {
            var dataVar = this.reportData[i].numberOfClick;
            if (dataVar > maxData)
            {
                maxData = dataVar;
            }
        }
        if (maxData > 0)
        {
            this.basicPointYForData = this.reportContentHeight / maxData;
        }
    };


    this.generateReport = function()
    {
        this.drawPointLine();
        this.drawPoint();
        this.drawNumberOfClickLabel();
        this.drawNumberOfDateLabel();
    };

//this.positionArray = [];
    this.drawNumberOfDateLabel = function ()
    {
        for(var i = 0; i < this.numberOfPoint; i++)
        {
            this.canvasContext.lineWidth = 1;
            var dataVar = this.reportData[i].createTimeText;
            var pointX = this.pointStartX + this.marginPointX * i;
            var pointY = this.pointStartY + this.bottomMarginForDate;
            var textMetrics = this.canvasContext.measureText(dataVar);

            this.canvasContext.beginPath();
            this.canvasContext.fillText(dataVar,pointX - textMetrics.width/2,pointY + 10);
            this.canvasContext.stroke();
        }
    };

    //this.positionArray = [];
    this.drawNumberOfClickLabel = function ()
    {
        for(var i = 0; i < this.numberOfPoint; i++)
        {
            this.canvasContext.lineWidth = 1;
            var dataVar = this.reportData[i].numberOfClick;
            var pointX = this.pointStartX + this.marginPointX * i;
            var pointY = this.pointStartY - this.basicPointYForData * dataVar;
            var textMetrics = this.canvasContext.measureText(dataVar);

            this.canvasContext.beginPath();
            this.canvasContext.fillText(dataVar,pointX - textMetrics.width/2,pointY - 10);
            this.canvasContext.stroke();
        }
    };

    this.drawPoint = function()
    {
        for(var i = 0; i < this.numberOfPoint; i++)
        {

            this.canvasContext.lineWidth = 1;
            var dataVar = this.reportData[i].numberOfClick;
            var pointX = this.pointStartX + this.marginPointX * i;
            var pointY = this.pointStartY - this.basicPointYForData * dataVar;

            this.canvasContext.beginPath();
            this.canvasContext.arc(pointX,pointY,this.pointRadius,0 , 2 * Math.PI,false);
            this.canvasContext.strokeStyle = "#33CCFF";
            this.canvasContext.fillStyle = "#33CCFF";
            this.canvasContext.fill();
            this.canvasContext.stroke();
        }
    };

    this.drawPointLine = function()
    {
        var lastPoint = [];
        for(var i = 0; i < this.numberOfPoint; i++)
        {

            this.canvasContext.lineWidth = 1;
            var dataVar = this.reportData[i].numberOfClick;
            var pointX = this.pointStartX + this.marginPointX * i;
            var pointY = this.pointStartY - this.basicPointYForData * dataVar;

            if (i > 0)
            {
                var lastPointX = lastPoint[0];
                var lastPointY = lastPoint[1];
                this.canvasContext.beginPath();
                this.canvasContext.moveTo(lastPointX,lastPointY);
                this.canvasContext.lineTo(pointX,pointY);
                this.canvasContext.strokeStyle = "#33FFCC";
                this.canvasContext.stroke();
            }
            lastPoint = [pointX,pointY];
        }
    };

    function translateWindowPositionToCanvasPosition(windowX, windowY)
    {
        var canvasRect = this.reportArea.getBoundingClientRect();
        var position = new Object();
        position["x"] = (windowX - canvasRect.left) * (this.reportArea.width / canvasRect.width);
        position["y"] = (windowY - canvasRect.top) * (this.reportArea.height / canvasRect.height);
        return position;
    };

    var reportObj = this;

    this.reportArea.onmousemove = function(e)
    {

        var position = translateWindowPositionToCanvasPosition.call(reportObj,e.clientX, e.clientY)

        for(var i = 0; i < reportObj.numberOfPoint; i++)
        {
            reportObj.canvasContext.lineWidth = 1;
            var dataVar = reportObj.reportData[i].numberOfClick;
            var pointX = reportObj.pointStartX + reportObj.marginPointX * i;
            var pointY = reportObj.pointStartY - reportObj.basicPointYForData * dataVar;

            var inArea = Math.abs(pointX - position.x) <= reportObj.onmouseoverEventAreaX;
            if (inArea){
                var data = reportObj.reportData[i].numberOfClick;

            }
        }
    };



    this.initReport();

    this.generateReport();
}


