document.getElementById("firstname").addEventListener("input", function() {
    var isValid = /^[A-Za-z]+$/.test(this.value);
    var errorElement = document.getElementById("firstname-error");
    errorElement.style.display = isValid ? "none" : "block";
    checkFormValidity();
});

document.getElementById("lastname").addEventListener("input", function() {
    var isValid = /^[A-Za-z]+$/.test(this.value);
    var errorElement = document.getElementById("lastname-error");
    errorElement.style.display = isValid ? "none" : "block";
    checkFormValidity();
});

document.getElementById("email").addEventListener("input", function() {
    var isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.value);
    var errorElement = document.getElementById("email-error");
    errorElement.style.display = isValid ? "none" : "block";
    checkFormValidity();
});

document.getElementById("password").addEventListener("input", function() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm-password").value;
    var passwordsMatch = password === confirmPassword;
    var errorElement = document.getElementById("password-match-error");
    errorElement.style.display = passwordsMatch ? "none" : "block";
    checkFormValidity();
});

document.getElementById("confirm-password").addEventListener("input", function() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm-password").value;
    var passwordsMatch = password === confirmPassword;
    var errorElement = document.getElementById("password-match-error");
    errorElement.style.display = passwordsMatch ? "none" : "block";
    checkFormValidity();
});

function checkFormValidity() {
    var registerButton = document.querySelector("button[type='submit']");
    var errorElements = document.querySelectorAll("span[id$='-error']:not([style*='none'])");
    if (errorElements.length === 0) {
        registerButton.disabled = false;
    } else {
        registerButton.disabled = true;
    }
}

document.getElementById("registerForm").addEventListener("submit", function(event) {
    event.preventDefault();

    var formData = new FormData(this);

    fetch('/api/users/register', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .then(data => {
            if (data.success) {
                alert('Registration successful!');
                window.location.href = '/login';
            } else {
                alert('Registration failed! Email already exists.');
            }
        })
        .catch(error => {
            console.error('Error during registration:', error);
            alert('An error occurred during registration. Please try again later.');
        });
});
