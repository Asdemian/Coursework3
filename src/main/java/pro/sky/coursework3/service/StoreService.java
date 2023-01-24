package pro.sky.coursework3.service;

import org.springframework.stereotype.Service;
import pro.sky.coursework3.exception.IncorrectParamException;
import pro.sky.coursework3.model.Color;
import pro.sky.coursework3.model.Size;
import pro.sky.coursework3.model.Sock;
import pro.sky.coursework3.model.SockWarehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class StoreService {

    private final Map<Sock, Integer> socks = new HashMap<>();

    public void coming(SockWarehouse sockWarehouse) {
        if (isNonValid(sockWarehouse)) {
            throw new IncorrectParamException();
        }
        Sock sock = sockWarehouse.getSock();
        if (socks.containsKey(sock)) {
            socks.replace(sock, socks.get(sock) + sockWarehouse.getQuantity());
        } else {
            socks.put(sock, sockWarehouse.getQuantity());
        }
    }

    public void expenditure(SockWarehouse sockWarehouse) {
        Sock sock = sockWarehouse.getSock();
        if (!socks.containsKey(sock) || isNonValid(sockWarehouse)) {
            throw new IncorrectParamException();
        }
        int available = socks.get(sock);
        int result = available - sockWarehouse.getQuantity();
        if (result < 0) {
            throw new IncorrectParamException();
        }
        socks.replace(sock, result);
    }

    private boolean isNonValid(SockWarehouse sockWarehouse) {
        Sock sock = sockWarehouse.getSock();
        return sock.getCottonPart() < 0 || sock.getCottonPart() > 100 ||
                sockWarehouse.getQuantity() <= 0;
    }

    public int count(String color,
                     float size,
                     int cottonMin,
                     int cottonMax) {
        Color c = Color.parse(color);
        Size s = Size.parse(size);
        if (Objects.isNull(c) || Objects.isNull(s) || cottonMin >= cottonMax ||
                cottonMin < 0 || cottonMax > 100) {
            throw new IncorrectParamException();
        }
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            Sock sock = entry.getKey();
            int available = entry.getValue();
            if (sock.getColor() == c && sock.getSize() == s &&
                    sock.getCottonPart() >= cottonMin &&
                    sock.getCottonPart() <= cottonMax) {
                return available;
            }
        }
        return 0;
    }
}
