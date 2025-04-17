# Hardware System Simulator

This project simulates a modular embedded hardware system using Java. It supports various devices such as sensors, displays, wireless modules, and motor drivers, all connected via configurable communication protocols. The system is capable of interpreting commands, interacting with simulated hardware, and logging protocol operations.

## 🔧 Features

- 📦 Add/remove devices dynamically (DHT11, BME280, MPU6050, LCD, OLED, etc.)
- 📡 Protocol support: I2C, SPI, UART, OneWire
- 🔄 Turn devices ON/OFF
- 📊 Read sensor data
- 🖥️ Display messages on LCD/OLED
- 📶 Send/receive messages via Bluetooth and WiFi
- ⚙️ Set motor speed for drivers like PCA9685, SparkFunMD
- 🗂️ Save operation logs per port
- ✅ Complies with constraints (O(1) operations, no for-each, allowed DS: ArrayList, LinkedList, Stack, Queue)

## 📁 Project Structure

```
.
├── Main.java                         # Entry point
├── hwSystem/                         # System logic and execution engine
│   ├── hwSystem.java                 # Core system controller
│   ├── Devices/                      # Device definitions
│   ├── Protocols/                    # Communication protocols (I2C, SPI, UART, OneWire)
```

## 📜 Usage

### Run the system

```bash
java Main.Main config.txt logs/ < scenario.txt > output.txt
```

- `config.txt`: System configuration (devices and ports)
- `logs/`: Output directory for port logs
- `scenario.txt`: Series of system commands

## 🧪 Supported Commands

- `addDev <devName> <portID> <devID>`
- `turnON <portID>`
- `turnOFF <portID>`
- `rmDev <portID>`
- `list ports`
- `list <Sensor|Display|WirelessIO|MotorDriver>`
- `readSensor <devID>`
- `printDisplay <devID> <message>`
- `writeWireless <devID> <message>`
- `readWireless <devID>`
- `setMotorSpeed <devID> <speed>`
- `exit`

## ✅ Example Devices

- **Sensors**: DHT11, BME280, MPU6050, GY951
- **Displays**: LCD, OLED
- **WirelessIO**: Bluetooth, WiFi
- **MotorDrivers**: PCA9685, SparkFunMD

## 📌 Notes

- Designed for OOP principles and strict memory/time constraints.
- Suitable for demonstrating protocol interaction and system management in embedded simulations.

---

Developed by **Alperen Çelik**  
Computer Engineering, GTU  
2025  
