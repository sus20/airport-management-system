export enum BaggageStatus {
    CHECKED_IN = 'CHECKED_IN',
    SCREENED = 'SCREENED',
    LOADED = 'LOADED',
    IN_TRANSIT = 'IN_TRANSIT',
    ARRIVED = 'ARRIVED',
    DELIVERED = 'DELIVERED',
    OFFLOADED = 'OFFLOADED',
    LOST = 'LOST'
}

export interface BaggageRequest {
    weight: number;
}

export interface BaggageStatusUpdateRequest {
    status: BaggageStatus;
}

export interface BaggageResponse {
    id: string;
    tagNumber: string;
    weight: number;
    status: BaggageStatus;
}