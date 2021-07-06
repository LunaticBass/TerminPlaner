'use strict';

//Public Globals
const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

let c_date = new Date(millisec);
let day = c_date.getDay();
let month = c_date.getMonth();
let year = c_date.getFullYear();

function setSelectedDate(){
	let millisec = $('.dates').data('millisec');
	let selectedDate = new Date(millisec);
	
    let selectedDayOfWeek = days[selectedDate.getDay()];
    let selectedDayString = selectedDayOfWeek + ", "+ selectedDate.getDate() +' '+ (months[selectedDate.getMonth()]) +' '+ selectedDate.getFullYear();            
    $('.appointment-date').html(selectedDayString); 
	
}

function renderCalendar(m, y) {
	setSelectedDate();
    //Month's first weekday
    let firstWeekDay = new Date(y, m, 1).getDay();
    let firstDay = (firstWeekDay == 0) ? 6 : firstWeekDay - 1;
    //Days in Month
    let d_m = new Date(y, m+1, 0).getDate();
    //Days in Previous Month
    let d_pm = new Date(y, m, 0).getDate();    
    
    let table = document.getElementById('dates');
    table.innerHTML = '';
    let s_m = document.getElementById('s_m');
    s_m.innerHTML = months[m] + ' ' + y;
    let date = 1;
    //remaing dates of last month
    let r_pm = (d_pm-firstDay) +1;
    for (let i = 0; i < 6; i++) {
        let row = document.createElement('tr');
        for (let j = 0; j < 7; j++) { // EZ
            if (i === 0 && j < firstDay) {  
                let cell = document.createElement('td');
                let span = document.createElement('span');
                let cellText = document.createTextNode(r_pm);
                span.classList.add('ntMonth');
                span.classList.add('prevMonth');                  
                cell.appendChild(span).appendChild(cellText);
                row.appendChild(cell);
                r_pm++;
            }
            else if (date > d_m && j <7) {
                if (j!==0) {
                    let i = 0; 
                    for (let k = j; k < 7; k++) {
                         i++                                             
                        let cell = document.createElement('td');
                        let span = document.createElement('span');
                        let cellText = document.createTextNode(i);
                        span.classList.add('ntMonth');                    
                        span.classList.add('nextMonth');                    
                        cell.appendChild(span).appendChild(cellText);
                        row.appendChild(cell);          
                    };                  
                }                
               break;
            }
            else {
                let cell = document.createElement('td');
                let span = document.createElement('span');
                let cellText = document.createTextNode(date);
                
                if (j == 6){
					span.classList.add('sunday');
				} else{
					span.classList.add('showEvent');
				}               
                
                if (date === c_date.getDate() && y === c_date.getFullYear() && m === c_date.getMonth()) {
                    span.classList.add('active');                    
                }
                
                // mark actual day
                let today = new Date();
                if (date === today.getDate() && y === today.getFullYear() && m === today.getMonth()) {
                    span.classList.add('bg-danger');                    
                }
                       
                cell.appendChild(span).appendChild(cellText);
                row.appendChild(cell);
                date++;
            }
        }
        table.appendChild(row);
    }
}

renderCalendar(month, year);


$(function(){
    $(document).on('click', '.prevMonth', function(){
        year = (month === 0) ? year - 1 : year;
        month = (month === 0) ? 11 : month - 1;
        renderCalendar(month, year);
    })
    
    $(document).on('click', '.nextMonth', function(){
        year = (month === 11) ? year + 1 : year;
        month = (month + 1) % 12;
        renderCalendar(month, year);
    })

    $(document).on('click', '.showEvent', function(){
        $('.showEvent').removeClass('active');
        $(this).addClass('active');           
                  	
       	let selectedDateInMillisec = new Date(year, month, $(this).text()).getTime();
        
        window.location = "/TerminPlaner/appointments/" + selectedDateInMillisec;
        
    })
    
    $(document).on('click', '.hide', function(){
        $('#event').addClass('d-none');
    })        

})

$(".link-delete").on("click", function (e){
	e.preventDefault();				
	$("#yesButton").attr("href", $(this).attr("href"));
	$("#confirmText").text("Are you sure you want to delete the appointment?");
	$("#confirmModal").modal();
});
        
