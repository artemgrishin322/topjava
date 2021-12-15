const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
              "url": mealAjaxUrl,
              "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return date.substring(0, 10) + " " + date.substring(11, 16);
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                    $(row).attr("data-meal-excess", data.excess);
            }
        })
    );
});

$('#startDate').datetimepicker({
    timepicker: false,
    datepicker: true,
    format: 'Y-m-d',
    onShow: function (ct) {
        let date = $('#endDate').val();
        this.setOptions({
            maxDate: date ? date : false
        })
    }
});

$('#endDate').datetimepicker({
    timepicker: false,
    datepicker: true,
    format: 'Y-m-d',
    onShow: function (ct) {
        let date = $('#startDate').val();
        this.setOptions({
            minDate: date ? date : false
        })
    }
});

$('#startTime').datetimepicker({
    timepicker: true,
    datepicker: false,
    format: 'H:i',
    step: 30,
    onShow: function (ct) {
        let time = $('#endTime').val();
        this.setOptions({
            maxTime: time ? time : false
        })
    }
});

$('#endTime').datetimepicker({
    timepicker: true,
    datepicker: false,
    format: 'H:i',
    step: 30,
    onShow: function (ct) {
        let time = $('#startTime').val();
        this.setOptions({
            minTime: time ? time : false
        })
    }
});

$('#dateTime').datetimepicker({
    format: 'Y-m-d H:i',
    step: 5
});