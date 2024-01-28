package Model;

public interface TCPConnectionListener {//описывает поведение класса, который взаимодействует по сети через протокол TCP
    void onConnectionReady(TCPConnection tcpConnection);
    void onReceiveObject(TCPConnection tcpConnection, Object object);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, Exception e);
}
