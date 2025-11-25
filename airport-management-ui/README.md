**# Airline Management System

This application is a React + TypeScript front-end system designed to manage essential airline operations. It provides a
clean interface for viewing and managing flights, passengers, baggage, and check-ins.

## Core Functions

### 1. Flight Management

* View All Flights: Browse complete flight schedules with detailed information
* Create & Edit Flights: Add new flights or modify existing ones
* Real-time Status Updates: Update flight status (Scheduled, Boarding, In Air, Landed, Cancelled)
* Gate & Terminal Management: Assign and modify gates and terminals
* Advanced Search: Filter flights by various criteria including flight number, airline, route, and time ranges
* Pricing Management: Set and update flight prices

### 2. Passenger Management

* Passenger Database: Comprehensive passenger information management
* Personal Details: Store names, gender, date of birth, and contact information
* Passport Management: Track passport numbers, countries, and expiry dates
* Advanced Search: Find passengers by name, contact info, or passport details
* Bulk Operations: Create, edit, and delete passenger records

### 3. Baggage Tracking

* Baggage Tracking: Monitor baggage throughout the journey
* Weight Management: Update and track baggage weights
* Status Updates: Real-time baggage status tracking (Checked-in, Screened, Loaded, In Transit, Arrived, Delivered,
  Offloaded, Lost)
* Search by ID: Quick baggage lookup by ID
* Weight Validation: Enforce maximum weight limits (25kg)

### 4. Check-In Handling

* Flight Check-ins: Create and manage passenger check-ins for flights
* Seat Assignment: Assign and update seat numbers with validation
* Multi-passenger Support: Handle multiple seats per check-in
* Flight-based Views: View all check-ins for specific flights
* Boarding Pass Generation: Digital boarding pass management
* Cancellation Support: Cancel check-ins with confirmation

## Technology Stack

* Frontend: React 18 with TypeScript
* Routing: React Router DOM
* Forms: React Hook Form with validation
* Styling: Custom CSS with modern responsive design
* HTTP Client: Axios for API communication
* State Management: React Hooks (useState, useEffect)

## 📁 Project Structure

```
airport-management-ui/
├── docker/              
├── node_modules/
├── src/
│ ├── components/       
│ │ ├── Baggage/    
│ │ ├── CheckIns/   
│ │ ├── Flights/        
│ │ ├── HomePage/       
│ │ └── Passengers/     
│ ├── services/       
│ │ ├── flightService.ts
│ │ ├── passengerService.ts
│ │ ├── baggageService.ts
│ │ └── checkInService.ts
│ ├── types/           
│ │ ├── flight.ts
│ │ ├── passenger.ts
│ │ ├── baggage.ts
│ │ └── checkIn.ts
│ ├── App.tsx      
│ ├── App.css       
│ └── main.jsx         
├── index.html      
├── package.json     
├── package-lock.json   
├── tsconfig.json             
├── tsconfig.app.json           
├── tsconfig.node.json       
├── vite.config.ts             
├── eslint.config.js           
└── README.md
```

## Key Components

### Flight Management

* FlightsManager.tsx - Main flight management interface
* FlightForm.tsx - Create/edit flight forms
* FlightSearch.tsx - Advanced flight search
* FlightDetails.tsx - Detailed flight view with real-time updates

### Passenger Management

* PassengersManager.tsx - Main passenger management interface
* PassengerForm.tsx - Create/edit passenger forms
* PassengerSearch.tsx - Passenger search functionality
* PassengerList.tsx - Passenger listing with cards

### Check-In Management

* CheckInsManager.tsx - Central check-in management hub
* CreateCheckIn.tsx - New check-in creation
* CheckInDetails.tsx - Detailed check-in information
* UpdateCheckInSeats.tsx - Seat assignment management
* CancelCheckIn.tsx - Check-in cancellation

### Baggage Management

* BaggageManager.tsx - Baggage management dashboard
* BaggageDetails.tsx - Baggage information display
* UpdateBaggageWeight.tsx - Weight update interface
* UpdateBaggageStatus.tsx - Status update interface

## Getting Started

### Prerequisites

* Node.js (version 14 or higher)
* npm or yarn
* Docker (version 20.10 or higher)
* Docker Compose (version 2.0 or higher)

### Installation

* Clone the repository
* You can start, stop, build, and remove all backend services using the provided helper script:

   ```
  ./docker.sh {up|down|delete|build}
  ./docker.sh up       # Start all services
  ./docker.sh down     # Stop services and remove volumes
  ./docker.sh delete   # Remove all containers, volumes, networks, orphans
  ./docker.sh build    # Build and start UI containers
  ```

* Install dependencies: npm install
* Start the development server: npm start or `npm run dev`
  Open http://localhost:5173 in your browser**