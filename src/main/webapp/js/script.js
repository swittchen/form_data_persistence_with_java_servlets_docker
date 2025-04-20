// Wait for the DOM to be fully loaded before attaching event listeners
document.addEventListener('DOMContentLoaded', () => {

    // Reference to the HTML form and the output container
    const form = document.querySelector('#messageForm');
    const output = document.querySelector('#output');

    // Attach an event listener to intercept form submission
    form.addEventListener('submit', (e) => {
        e.preventDefault(); // Prevent default form behavior (page reload)

        // Gather form data using the FormData API
        const formData = new FormData(form);

        // Convert form data to a plain object for JSON serialization
        const dataToSend = Object.fromEntries(formData.entries());

        // Send the form data as a JSON POST request to the backend
        fetch('/game', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Specify JSON content type
            },
            body: JSON.stringify(dataToSend), // Serialize the data to JSON
        })
        .then((response) => {
            // Handle HTTP errors explicitly
            if (!response.ok) {
                throw new Error(`Error! Status: ${response.status}`);
            }
            return response.json(); // Parse response as JSON
        })
        .then((data) => {
            // Display the server's response in the output container
            output.innerHTML = `<p>Status: ${data.status}</p><p>Message: ${data.echoMessage}</p>`;
        })
        .catch((error) => {
            // Handle network or server-side errors
            output.innerHTML = `<p>Error: ${error.message}</p>`;
        });
    });
});
