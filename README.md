# School Grades Spring Security

Simple system for managing school grades using authentication and authorization methods.

## Report card endpoints

| método/rota | query params | body |
|-------|-------|-------|
| <kbd>POST /report-card</kbd> | - | [view](#post-report-card) |
| <kbd>GET /report-card/teacher</kbd> | username | [view](#get-report-card-teacher) |
| <kbd>GET /report-card/student</kbd> | username | [view](#get-report-card-student) |
| <kbd>PUT /report-card</kbd> | id | [view](#put-report-card) |
-------

<h3 id="post-report-card">POST /report-card</h3>

**Request body**
```json
{
    "teacherUsername": "teacher",
    "schoolSubject": "MATHS",
    "studentUsername": "student",
    "schoolYear": "2024",
    "grades": ["10.00", "10.00", "10.00", null]
}
```
-----

<h3 id="get-report-card-teacher">GET /report-card/teacher</h3>

**Response body**
```json
[
    {
        "id": "18bd0387-b81f-4248-a5fc-52ec813c0a6d",
        "teacherUsername": "teacher",
        "schoolSubject": "MATHS",
        "studentUsername": "student",
        "schoolYear": "2024",
        "grades": ["10.00", "10.00", "10.00", null]
    }
]
```
-----

<h3 id="get-report-card-student">GET /report-card/student</h3>

**Response body**
```json
[
    {
        "id": "18bd0387-b81f-4248-a5fc-52ec813c0a6d",
        "teacherUsername": "teacher",
        "schoolSubject": "MATHS",
        "studentUsername": "student",
        "schoolYear": "2024",
        "grades": ["10.00", "10.00", "10.00", null]
    }
]
```
-----

<h3 id="put-report-card">PUT /report-card</h3>

**Request body**
```json
{
    "teacherUsername": "teacher",
    "schoolSubject": "MATHS",
    "studentUsername": "student",
    "schoolYear": "2024",
    "grades": ["10.00", "10.00", "10.00", "10.00"]
}
```
-----

## Auth features

### **v1.0**

- In version 1.0, authentication is done by the <b>"Basic auth"</b> header in all requests.  
- The <b>passwords</b> are saved in the database <b>without encryption</b>.

-----

#### +ENDPOINTS

<kbd style="font-size:17px;">POST /authentication/register</kbd>

**Request body**
```json
{
    "username": "teacher",
    "password": "1234",
    "role": "TEACHER"
}
```
-----

### **v2.0**

- In version 2.0, authentication can be done by <b>JWT</b> in the bearer authorization header.  
- The <b>passwords</b> are saved in the database <b>with encryption</b>.  
- <b>RSA keys</b> to be used in the JWT enconding. Execute the script "gen-keys.sh".  
- Creating a <b>default user</b> when the application starts.

-----

#### +ENDPOINTS (addition)

<kbd style="font-size:17px;">POST /authentication/login</kbd>

**Request Header**

```bash
Authorization: Basic dGVhY2hlcjoxMjM0
```

**Response body**
```bash
eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzY2hvb2wtZ3JhZGVzLXNwcmluZy1zZWN1cml0eSIsInN1YiI6InByb2YiLCJleHAiOjE3MzgxODU4NTUsImlhdCI6MTczODE4NTg1NCwic2NvcGUiOiJST0xFX1RFQUNIRVIifQ.XKLcsVdsFUO8Bep2XIe-TqgxNSzDPCF1cj-yhtvpB1mbO_O1dbQHam8mOm8vDPvzuNAyulPr9aR-MCA8S28xHYXH33oND9AhsanUhNfLdhXUQFBZON9KeOM0-YqHNNoKhqWsdeV7-7bCIghIW0SzBQXulpnlJ26QfsKq2JnngmUMO5Dwu8e_uMpUCi06gWcZFUFYgTlyqf795lFzHbEggcn46Q-ol5eocGukuoRr2ST6hiMpg2DBBfXfGUZSNGKlx3Oqrn_d15KADrv3pzdIIoukkvPBfLzmqFaips35KT8OjW4_ooZjAOFDyqfsGPwcyIyOLzXpXBtPAmeYd9tZKw
```
-----

### **v3.0**

- Refresh the expired access token using obtained refresh token


#### +ENDPOINTS

-----

<kbd style="font-size:17px;">POST /authentication/login</kbd>

**Request Header**

```bash
Authorization: Basic dGVhY2hlcjoxMjM0
```

**Response body**

```json
{
	"accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzY2hvb2wtZ3JhZGVzLXNwcmluZy1zZWN1cml0eSIsInN1YiI6InByb2YiLCJleHAiOjE3MzgxODU4NTUsImlhdCI6MTczODE4NTg1NCwic2NvcGUiOiJST0xFX1RFQUNIRVIifQ.XKLcsVdsFUO8Bep2XIe-TqgxNSzDPCF1cj-yhtvpB1mbO_O1dbQHam8mOm8vDPvzuNAyulPr9aR-MCA8S28xHYXH33oND9AhsanUhNfLdhXUQFBZON9KeOM0-YqHNNoKhqWsdeV7-7bCIghIW0SzBQXulpnlJ26QfsKq2JnngmUMO5Dwu8e_uMpUCi06gWcZFUFYgTlyqf795lFzHbEggcn46Q-ol5eocGukuoRr2ST6hiMpg2DBBfXfGUZSNGKlx3Oqrn_d15KADrv3pzdIIoukkvPBfLzmqFaips35KT8OjW4_ooZjAOFDyqfsGPwcyIyOLzXpXBtPAmeYd9tZKw",
	"refreshToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzY2hvb2wtZ3JhZGVzLXNwcmluZy1zZWN1cml0eSIsInN1YiI6InByb2YiLCJleHAiOjE3Mzg3OTA3MDUsImlhdCI6MTczODE4NTkwNX0.Jt32KFebCHE273P5oVIDyh3PUejqiWZ0AgdmNFXm7VFZ29XnWBQuWUxCEp4S9pLB4UBU-fANiAKyet4hRvNyWfkhChUvbZyg13uWgcmKu9-vtppFd_JTTt3XH_nkV3Hiw3c7WVpwMWBOOY95MKQCcLTIpq19RC8O1hCM0IOVY6woTcfnHUcy1POfwbqXOpuTzQBhdYzwBDBeo_IEBRm-7LkGW6pP17CBFIudvaicMLsmiGVyfTLecxGrMAdpkwD9jICDgxxf9yPeEqulUn1OnNyuuxc7U2Pv3xM-1hpLg2PUUy3P7hjXcMz7DswYaEuFmMyVwZNYvxqjDG1pT8u-pA"
}
```
-----

<kbd style="font-size:17px;">POST /authentication/refresh</kbd>

**Request body**

```json
{
    "refreshToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzY2hvb2wtZ3JhZGVzLXNwcmluZy1zZWN1cml0eSIsInN1YiI6InByb2YiLCJleHAiOjE3Mzg3OTA3MDUsImlhdCI6MTczODE4NTkwNX0.Jt32KFebCHE273P5oVIDyh3PUejqiWZ0AgdmNFXm7VFZ29XnWBQuWUxCEp4S9pLB4UBU-fANiAKyet4hRvNyWfkhChUvbZyg13uWgcmKu9-vtppFd_JTTt3XH_nkV3Hiw3c7WVpwMWBOOY95MKQCcLTIpq19RC8O1hCM0IOVY6woTcfnHUcy1POfwbqXOpuTzQBhdYzwBDBeo_IEBRm-7LkGW6pP17CBFIudvaicMLsmiGVyfTLecxGrMAdpkwD9jICDgxxf9yPeEqulUn1OnNyuuxc7U2Pv3xM-1hpLg2PUUy3P7hjXcMz7DswYaEuFmMyVwZNYvxqjDG1pT8u-pA"
}
```

**Response body**

```json
{
	"accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzY2hvb2wtZ3JhZGVzLXNwcmluZy1zZWN1cml0eSIsInN1YiI6InByb2YiLCJleHAiOjE3MzgxODU4NTUsImlhdCI6MTczODE4NTg1NCwic2NvcGUiOiJST0xFX1RFQUNIRVIifQ.XKLcsVdsFUO8Bep2XIe-TqgxNSzDPCF1cj-yhtvpB1mbO_O1dbQHam8mOm8vDPvzuNAyulPr9aR-MCA8S28xHYXH33oND9AhsanUhNfLdhXUQFBZON9KeOM0-YqHNNoKhqWsdeV7-7bCIghIW0SzBQXulpnlJ26QfsKq2JnngmUMO5Dwu8e_uMpUCi06gWcZFUFYgTlyqf795lFzHbEggcn46Q-ol5eocGukuoRr2ST6hiMpg2DBBfXfGUZSNGKlx3Oqrn_d15KADrv3pzdIIoukkvPBfLzmqFaips35KT8OjW4_ooZjAOFDyqfsGPwcyIyOLzXpXBtPAmeYd9tZKw",
	"refreshToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzY2hvb2wtZ3JhZGVzLXNwcmluZy1zZWN1cml0eSIsInN1YiI6InByb2YiLCJleHAiOjE3Mzg3OTA3MDUsImlhdCI6MTczODE4NTkwNX0.Jt32KFebCHE273P5oVIDyh3PUejqiWZ0AgdmNFXm7VFZ29XnWBQuWUxCEp4S9pLB4UBU-fANiAKyet4hRvNyWfkhChUvbZyg13uWgcmKu9-vtppFd_JTTt3XH_nkV3Hiw3c7WVpwMWBOOY95MKQCcLTIpq19RC8O1hCM0IOVY6woTcfnHUcy1POfwbqXOpuTzQBhdYzwBDBeo_IEBRm-7LkGW6pP17CBFIudvaicMLsmiGVyfTLecxGrMAdpkwD9jICDgxxf9yPeEqulUn1OnNyuuxc7U2Pv3xM-1hpLg2PUUy3P7hjXcMz7DswYaEuFmMyVwZNYvxqjDG1pT8u-pA"
}
```
