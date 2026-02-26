
function submitApplication(e) {
    e.preventDefault(); // You can ignore this; prevents the default form submission!

    const jobs = document.getElementsByName('job');
    let alerted = false;
    for (const job of jobs) {
        if (job.checked) {
            alert("Thank you for applying to be a " + job.value + "!");
            alerted = true;
        };
    };
    if (!alerted) {
        alert("Please select a job!");
    };
};