package ru.itmo.core.common.exchange.response.serverResponse.unidirectional;

// Command response status

public enum CRStatus {
    OK,
    ERROR,
    NEUTRAL; // Перевод : нейтральный, неопределенный
}
