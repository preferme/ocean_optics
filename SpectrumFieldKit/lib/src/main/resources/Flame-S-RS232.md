# Flame–S 串口通信和控制

<!-- TOC -->
* [Flame–S 串口通信和控制](#flames-串口通信和控制)
  * [一、硬件描述](#一硬件描述)
  * [二、指令集](#二指令集)
    * [命令语法](#命令语法)
  * [三、命令描述](#三命令描述)
    * [3.1 Add Scans](#31-add-scans)
    * [3.2 Pixel Boxcar Width](#32-pixel-boxcar-width)
    * [3.3 Set Data Compression](#33-set-data-compression)
    * [3.4 Integration Time (16 bit)](#34-integration-time-16-bit)
    * [3.5 Integration Time (32 bit)](#35-integration-time-32-bit)
    * [3.6 Lamp Enable](#36-lamp-enable)
    * [3.7 Baud Rate](#37-baud-rate)
    * [3.8 Clear Memory](#38-clear-memory)
    * [3.9 Data Storage Mode](#39-data-storage-mode)
    * [3.10 Pixel Mode](#310-pixel-mode)
    * [3.11 Spectral Acquisition](#311-spectral-acquisition)
    * [3.12 Trigger Mode](#312-trigger-mode)
    * [3.13 Set FPGA Register Value](#313-set-fpga-register-value)
    * [3.14 ASCII Data Mode](#314-ascii-data-mode)
    * [3.15 Binary Data Mode](#315-binary-data-mode)
    * [3.16 Checksum Mode](#316-checksum-mode)
    * [3.17 Version Number Query](#317-version-number-query)
    * [3.18 Calibration Constants](#318-calibration-constants)
    * [3.19 Query Variable](#319-query-variable)
<!-- TOC -->

* Flame是一种基于微控制器的微型光纤，可以通过USB或RS-232进行通信。
* 本文档包含通过RS-232接口控制Flame所需的命令信息。

## 一、硬件描述

* Flame采用`Cypress FX2`微控制器，该微控制器具有增强型`8051`内核，并集成`USB2.0`协议栈。
* 程序代码和数据系数存储在外部 $E^2PROM$ 中，在启动时通过 $I^2C$ 总线加载。

## 二、指令集

### 命令语法

* 下表显示了命令列表以及引入它们的微码版本号。
* 所有命令都由一个ASCII字符组成(通过串行端口传递)，后面跟着一些数据。
* 数据的长度取决于命令。
* 数据的格式是ASCII或二进制（默认）。
* 为了确保准确的通信，对于可接受的命令，所有命令都以ACK （ASCII 6）响应，
* 对于不可接受的命令（即指定的数据值超出范围），所有命令都以NAK （ASCII 21）响应。

1. 使用“a”命令设置ASCII模式，使用“b”命令设置二进制模式。

   - 在ASCII数据值模式下，Flame将命令“回显”到RS-232端口。
   - 在二进制模式下，(除特别注明外)所有数据都以16位无符号整数（WORDs）的形式传递，MSB后跟LSB。
2. 通过发出“v命令”（版本号查询），可以通过查看响应（ASCII或二进制）来确定数据模式。
3. 在典型的数据采集会话中，用户发送命令来实现所需的频谱采集参数（积分时间等）。

   - 然后用户发送命令，使用前面设置的参数获取光谱（S命令）。
4. 如果需要，波特率可以在这个序列的开始改变，以加快数据传输过程。


| 字符 | 描述                                                          |  | 字符 | 描述                                                                                      |
|----|-------------------------------------------------------------|--|----|-----------------------------------------------------------------------------------------|
| A  | [Adds scans](#31-add-scans)                                 |  | B  | [Set Pixel Boxcar](#32-pixel-boxcar-width)                                              |
| G  | [Set Data Compression](#33-set-data-compression)            |  |    |                                                                                         |
| I  | [Sets integration time](#34-integration-time-16-bit)        |  | i  | [Sets integration time](#35-integration-time-32-bit)                                    |
| J  | [Sets Lamp Enable Line](#36-lamp-enable)                    |  | K  | [Changes baud rate](#37-baud-rate)                                                      |
| L  | [Clear Memory](#38-clear-memory)                            |  | M  | [Set Data Storage Mode](#39-data-storage-mode)                                          |
| P  | [Partial Pixel Mode](#310-pixel-mode)                       |  | S  | [Starts spectral acquisition with previously set parameters](#311-spectral-acquisition) |
| T  | [Sets trigger mode](#312-trigger-mode)                      |  | W  | [~~Query scans in memory~~](#313-set-fpga-register-value)                               |
| a  | [Set ASCII mode for data values](#314-ascii-data-mode)      |  | b  | [Set binary mode for data values](#315-binary-data-mode)                                |
| k  | [Sets Checksum mode](#316-checksum-mode)                    |  | v  | [Provides microcode version](#317-version-number-query)                                 |
| x  | [Sets calibration coefficients](#318-calibration-constants) |  | ?  | [Queries parameter values](#319-query-variable)                                         |

## 三、命令描述

* 下面是所有Flame命令的详细描述。
* `{}` 表示被解释为ASCII或二进制（默认）的数据值。
* `默认值`为上电时该参数的值。

### 3.1 Add Scans

* 设置要相加的离散光谱的数目。
* 由于 Flame 有能力返回32位值，原始16位ADC值的溢出不是一个问题。

| Command Syntax: | A{DATA WORD} |
|-----------------|--------------|
| Range:          | 1-5000       |
| Default value:  | 1            |
| Response:       | ACK or NAK   |

### 3.2 Pixel Boxcar Width

* 设置一起平均的像素数。
* n的值指定了向右n像素和向左n像素的平均值。
* 这个例程使用32位整数，因此不会发生中间溢出；但是，在传输数据之前，结果被截断为16位整数。
* 这个数学运算是在每个像素值被传送出去之前执行的。
* 大于~3的值将超过值之间的空闲时间，并减慢整个传输过程。

| Command Syntax: | B{DATA WORD} |
|-----------------|--------------|
| Range:          | 0 - 15       |
| Default value:  | 0            |
| Response:       | ACK or NAK   |

### 3.3 Set Data Compression

指定从 Flame 传输的数据是否应该被压缩以加快数据传输速率。

| Command Syntax: | G{DATA WORD}                                  |
|-----------------|-----------------------------------------------|
| Range:          | 0  - Compression off<br/> !0 - Compression on |
| Default value:  | 0                                             |
| Response:       | ACK or NAK                                    |

### 3.4 Integration Time (16 bit)

* 将 Flame 的集成时间（以 ***毫秒*** 为单位）设置为指定的值。

| Command Syntax: | I{16 bit DATA WORD} |
|-----------------|---------------------|
| Range:          | 1 - 65000           |
| Default value:  | 10                  |
| Response:       | ACK or NAK          |

### 3.5 Integration Time (32 bit)

* 将 Flame 的集成时间（以 ***微秒*** 为单位）设置为指定的值。

| Command Syntax: | i{32 bit DATA WORD} |
|-----------------|---------------------|
| Range:          | 1000 - 65,000,000   |
| Default value:  | 10,000              |
| Response:       | ACK or NAK          |

### 3.6 Lamp Enable

* 将 Flame 的灯启用行设置为指定的值。

| Command Syntax: | J{DATA WORD}                                                                                |
|-----------------|---------------------------------------------------------------------------------------------|
| Value:          | 0 = Light source/strobe off—Lamp Enable low<br/>1 = Light source/strobe on—Lamp Enable high |
| Default value:  | 0                                                                                           |
| Response:       | ACK or NAK                                                                                  |

### 3.7 Baud Rate

* 设置 Flame 的波特率。

| Command Syntax: | K{DATA WORD}                                                              |
|-----------------|---------------------------------------------------------------------------|
| Value:          | 0=2400  1=4800  2=9600<br/>3=19200 4=38400 6=115200  <br/>5=Not Supported |
| Default value:  | 2                                                                         |
| Response:       | See below                                                                 |

* 更改波特率时，请遵循以下顺序：

  1. 控制程序以期望的波特率发送K，以旧波特率通信
  2. Flame以旧波特率响应ACK，否则它以NAK响应，并且进程被中止
  3. 控制程序等待时间超过50毫秒
  4. 控制程序发送K，然后是所需的波特率。
  5. Flame以新波特率响应ACK，否则使用NAK和旧波特率响应
* 如果任何一步出现偏差，则使用先前的波特率
* 例如，在ASCII模式下，将波特率从9600更改为115200。

  - 在9600波特，发送“K6<enter>”
  - 你将收到一个9600波特的ACK
  - 发送“K6<enter>”在115200。（注意“K”没有回显，但“6”有回显。）
  - 您将收到两个ACK字符和115200波特的提示。

### 3.8 Clear Memory

* 根据指定的值清除频谱数据内存。
* 清除内存是立即的，因为只有指针值被重新初始化。

<font color=red face="黑体">注意：</font>**当执行`Clear memory`命令时，所有存储的光谱都将丢失。**

| Command Syntax: | L{DATA WORD}                                          |
|-----------------|-------------------------------------------------------|
| Value:          | 0= Clear spectral memory<br/>1= Clear spectral memory |
| Default value:  | N/A                                                   |
| Response:       | ACK or NAK                                            |

### 3.9 Data Storage Mode

* 设置未来光谱采集的数据存储模式。

| Command Syntax: | M{DATA WORD}                       |
|-----------------|------------------------------------|
| Value:          | 0= 扫描通过串口传输<br/>1= 扫描存储在光谱存储器中，不传输 |
| Default value:  | 0                                  |
| Response:       | ACK or NAK                         |

### 3.10 Pixel Mode

* 指定传输哪些像素。
* 当每次扫描都获得所有像素时，这个参数决定哪些像素将被传输出串行端口。

| Command Syntax: | P{DATA WORD}                                                                                             |
|-----------------|----------------------------------------------------------------------------------------------------------|
| Value:          | 0= 全部2048像素<br/>1= 每第n个像素不取平均值 <br/>2= N/A <br/>3= 像素x到y每n个像素  <br/>4= 0到2047之间最多10个随机选择的像素（p1， p2，…p10） |
| Default value:  | 0                                                                                                        |
| Response:       | ACK or NAK                                                                                               |

* 由于大多数应用只需要频谱的一个子集，这种模式可以大大减少传输频谱所需的时间，同时仍然提供所有所需的数据。
* 当连接plc或其他处理设备时，此模式很有帮助。

| Example                                                                                        |
|------------------------------------------------------------------------------------------------|
| `P0`                                                                                           |
| `P1<Enter>`  <br/>`N<Enter>`                                                                   |
| `P2` N/A                                                                                       |
| `P3<Enter>`  <br/>`x<Enter>`  <br/>`y<Enter>`  <br/>`n<Enter>`                                 |
| `P4<Enter>`  <br/>`p1<Enter>`  <br/>`p2<Enter>`  <br/>`p3<Enter>`  <br/>...  <br/>`p10<Enter>` |

### 3.11 Spectral Acquisition

* 获取具有当前操作参数集的光谱。
* 在执行时，该命令决定所需的内存量。
* 如果不存在足够的内存，则立即返回ETX （ASCII 3）并且不获取光谱。
* 一旦数据被获取和存储，就发送STX （ASCII 2）。
* 如果“数据存储模式”为“0”，则立即传输数据。

| Command Syntax: | S                              |
|-----------------|--------------------------------|
| Response:       | 如果成功，则STX后面跟着数据<br/> 如果失败，则ETX |

返回的光谱格式包括指示扫描号、通道号、像素模式等的标头。格式如下：

- WORD 0xFFFF - spectrum的起始点
- WORD 数据大小标志（0 -数据是WORD的，1 -数据是DWORD的）
- WORD 累计扫描次数
- WORD 积分时间（毫秒）
- WORD FPGA建立基线值（MSW）
- WORD FPGA建立基线值（MSW）
- WORD 像素模式
- WORDs 如果像素模式不为0，则表示传递给像素模式命令的参数(P)
- (D)WORDs 频谱数据 取决于`数据大小标志`
- WORD 0xFFFD -频谱结束

### 3.12 Trigger Mode

* 将 Flame 的外部触发模式设置为指定的值。

| Command Syntax: | T{DATA WORD}                                                                        |
|-----------------|-------------------------------------------------------------------------------------|
| Value:          | 0= 正常（自由运行）模式<br/>1= 软件触发方式 <br/>2= 外部硬件级别触发模式 <br/>3= 外部同步触发方式  <br/>4= 外部硬件边缘触发模式 |
| Default value:  | 0                                                                                   |
| Response:       | ACK or NAK                                                                          |

### 3.13 Set FPGA Register Value

* 在FPGA内设置适当的寄存器。
* 寄存器设置列表在USB命令集信息中。
* 该命令需要两个数据值，一个用于指定寄存器，另一个用于指定值。

| Command Syntax: | W{DATA WORD 1}{DATA WORD 2}                                               |
|-----------------|---------------------------------------------------------------------------|
| Value:          | Data Word 1 – FPGA Register address<br/>Data Word 2 – FPGA Register Value |
| Default value:  | N/A                                                                       |
| Response:       | ACK or NAK                                                                |

### 3.14 ASCII Data Mode

* 设置将数据值解释为ASCII的模式。
* 这种模式只允许无符号整数值（0 - 65535），数据值以回车符（ASCII 13）或换行符（ASCII 10）结束。
* 在这种模式下，Flame “回显”命令和数据值返回RS-232端口。

| Command Syntax: | aA         |
|-----------------|------------|
| Default value:  | N/A        |
| Response:       | ACK or NAK |

* 该命令要求发送字符串“aA”，不带任何CR或LF。这是为了确保不会无意中进入该模式。
* 对版本号查询（v命令）的清晰响应表明 Flame 处于ASCII数据模式。

### 3.15 Binary Data Mode

* 设置将数据值解释为二进制的模式。
* 这种模式只允许16位无符号整数值（0 - 65535），MSB后跟LSB

| Command Syntax: | bB              |
|-----------------|-----------------|
| Default value:  | 开机时的默认值-不被Q命令改变 |
| Response:       | ACK or NAK      |

* 该命令要求发送字符串“bB”，不带任何CR或LF。这是为了确保不会无意中进入该模式。

### 3.16 Checksum Mode

* 指定 Flame 是否生成并传输光谱数据的16位校验和。
* 这个校验和可以用来测试光谱数据的有效性，当需要可靠的数据扫描时，建议使用它。

| Command Syntax: | k{DATA WORD}                      |
|-----------------|-----------------------------------|
| Value:          | 0 = 不传输校验和的值<br/>!0= 在扫描结束时发送校验和值 |
| Default value:  | 0                                 |
| Response:       | ACK or NAK                        |

### 3.17 Version Number Query

* 返回在微控制器上运行的代码的版本号。
* 返回值1000被解释为1.00.0。

| Command Syntax: | v                                 |
|-----------------|-----------------------------------|
| Default value:  | N/A                               |
| Response:       | ACK 之后跟随 {DATA WORD}              |

### 3.18 Calibration Constants

* 校准常数
* 将16个可能的校准常数之一写入EEPROM。
* 校准常数由x后面的第一个`DATA WORD`指定。
* 校准常数存储为最大长度为15个字符的`ASCII字符串`。字符串不会检查它是否有意义。

| Command Syntax: | x{DATA WORD}{ASCII STRING}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
|-----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Value:          | 0- Serial Number<br/>1- 0阶波长校准系数  <br/>2- 1阶波长校准系数  <br/>3- 2阶波长校准系数  <br/>4- 3阶波长校准系数  <br/>5- Stray light constant  <br/>6- 0阶非线性修正系数  <br/>7- 1阶非线性修正系数  <br/>8- 2阶非线性修正系数  <br/>9- 3阶非线性修正系数  <br/>10- 4阶非线性修正系数  <br/>11- 5阶非线性修正系数  <br/>12- 6阶非线性修正系数  <br/>13- 7阶非线性修正系数  <br/>14- 非线性标定的多项式阶  <br/>15- Optical bench configuration: gg fff sss  <br/>gg – Grating #, fff – filter wavelength, sss – slit size  <br/>16- Flame configuration: AWL V  <br/>A – Array coating Mfg, W – Array wavelength (VIS, UV, OFLV), L – L2 lens installed, V – CPLD Version  <br/>17,18,19-Reserved |
| Default value:  | N/A                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Response:       | ACK or NAK                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |

* 要查询这些指定的常数，请使用 `?x{DATA WORD}` 格式的命令

### 3.19 Query Variable

* 返回对应参数的当前值。
* 该命令的语法需要两个ASCII字符。第二个ASCII字符对应于（可接受的值为B、A、I、K、T、J、y）的命令字符。
* 该命令的一个特殊情况是?x（小写），它需要传递一个额外的数据字来指示要查询哪个校准常数。

| Command Syntax: | ?{ASCII character}   |
|-----------------|----------------------|
| Default value:  | N/A                  |
| Response:       | ACK 之后跟随 {DATA WORD} |
