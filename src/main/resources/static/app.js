const responseBox = document.getElementById("responseBox");

function showResponse(data) {
    responseBox.textContent = JSON.stringify(data, null, 2);
}

async function sendRequest(url, method, body = null) {
    const options = {
        method: method,
        headers: {
            "Content-Type": "application/json"
        }
    };

    if (body !== null) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(url, options);
    const text = await response.text();

    let data;

    try {
        data = text ? JSON.parse(text) : { message: "Success" };
    } catch {
        data = { message: text };
    }

    if (!response.ok) {
        throw data;
    }

    return data;
}

document.getElementById("residentForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    try {
        const data = await sendRequest("/api/residents", "POST", {
            name: document.getElementById("residentName").value,
            roomNumber: document.getElementById("roomNumber").value
        });

        showResponse(data);
        event.target.reset();
    } catch (error) {
        showResponse(error);
    }
});

document.getElementById("mealForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    try {
        const data = await sendRequest("/api/menu/meals", "POST", {
            weekStartDate: document.getElementById("weekStartDate").value,
            mealDate: document.getElementById("mealDate").value,
            mealType: document.getElementById("mealType").value,
            foodName: document.getElementById("foodName").value
        });

        showResponse(data);
        event.target.reset();
    } catch (error) {
        showResponse(error);
    }
});

document.getElementById("feedbackForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    try {
        const data = await sendRequest("/api/feedback", "POST", {
            residentId: Number(document.getElementById("feedbackResidentId").value),
            mealId: Number(document.getElementById("feedbackMealId").value),
            rating: Number(document.getElementById("rating").value),
            comment: document.getElementById("comment").value
        });

        showResponse(data);
        event.target.reset();
    } catch (error) {
        showResponse(error);
    }
});

document.getElementById("optOutForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    try {
        const data = await sendRequest("/api/residents/opt-out", "POST", {
            residentId: Number(document.getElementById("optOutResidentId").value),
            optOutDate: document.getElementById("optOutDate").value
        });

        showResponse(data);
        event.target.reset();
    } catch (error) {
        showResponse(error);
    }
});

document.getElementById("headcountButton").addEventListener("click", async () => {
    const date = document.getElementById("headcountDate").value;

    if (!date) {
        showResponse({ message: "Select a date first" });
        return;
    }

    try {
        const response = await fetch(
            `/api/dashboard/headcount?date=${date}`
        );

        const data = await response.json();

        if (!response.ok) {
            throw data;
        }

        document.getElementById("headcountResult").innerHTML = `
            <p><b>Total Residents:</b> ${data.totalResidents}</p>
            <p><b>Opted-Out Residents:</b> ${data.optedOutResidents}</p>
            <p><b>Expected Headcount:</b> ${data.expectedHeadcount}</p>
        `;

        showResponse(data);
    } catch (error) {
        showResponse(error);
    }
});

document.getElementById("loadResidentsButton").addEventListener("click", async () => {
    try {
        const response = await fetch("/api/residents");
        const residents = await response.json();

        if (!response.ok) {
            throw residents;
        }

        let table = `
            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Room Number</th>
                </tr>
        `;

        residents.forEach(resident => {
            table += `
                <tr>
                    <td>${resident.id}</td>
                    <td>${resident.name}</td>
                    <td>${resident.roomNumber}</td>
                </tr>
            `;
        });

        table += "</table>";

        document.getElementById("residentList").innerHTML = table;
        showResponse(residents);
    } catch (error) {
        showResponse(error);
    }
});