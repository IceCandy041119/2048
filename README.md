# 2048 游戏

这是一个基于 Java Swing 实现的 2048 游戏项目，支持动画效果、背景音乐播放以及键盘和鼠标操作。

## 功能特性

- **2048 游戏核心逻辑**：支持上下左右滑动合并数字，自动生成新数字。
- **动画效果**：实现了平滑的移动、合并和新数字生成动画。
- **背景音乐**：支持背景音乐播放、暂停、切换上一首/下一首。
- **键盘操作**：通过方向键控制游戏，支持空格键暂停/恢复音乐，`N` 键切换下一首音乐，`M` 键切换上一首音乐，`R` 键重新开始游戏。
- **鼠标操作**：支持通过鼠标点击控制音乐播放、切换和游戏重启。
- **游戏结束检测**：当没有可移动或合并的数字时，显示游戏结束提示。

## 项目结构
.    
├── .gitignore    
├── README.md   
├── pic/ # 存放游戏图片资源   
├── rubbish/ # 存放无用的音频文件   
├── sound/ # 存放背景音乐文件   
├── src/ # 项目源码    
│    
├── Main/ # 主入口    
│    
├── function/    
    │   
    │    
    ├── core/ # 游戏核心逻辑    
    │  
    │  
    ├── music/ # 音乐播放相关功能   
    │  
    │  
    ├── shell/ # 游戏界面相关功能   

## 文件说明

### 核心逻辑

- **[`GameStruct`](src/function/core/GameStruct.java)**：存储游戏矩阵、分数等状态。
- **[`Move`](src/function/core/Move.java)**：实现数字的移动和合并逻辑。
- **[`RandomNumberGenerate`](src/function/core/RandomNumberGenerate.java)**：生成随机数字。
- **[`IsGameOver`](src/function/core/IsGameOver.java)**：检测游戏是否结束。
- **[`PrintSquare`](src/function/core/PrintSquare.java)**：打印游戏矩阵到控制台。

### 音乐功能

- **[`PlayBGM`](src/function/music/PlayBGM.java)**：实现背景音乐的播放、暂停、切换功能。
- **[`MoveSound`](src/function/music/MoveSound.java)**：播放移动音效。

### 界面功能

- **[`Frame`](src/function/shell/Frame.java)**：主游戏窗口。
- **[`GamePanel`](src/function/shell/GamePanel.java)**：游戏主界面，负责绘制游戏矩阵和动画。
- **[`GameBackground`](src/function/shell/GameBackground.java)**：绘制游戏背景和网格。
- **[`MusicPlayer`](src/function/shell/MusicPlayer.java)**：音乐控制面板。

## 运行方法
`javac -d bin src/Main/Main.java`
`java -cp bin src.Main.Main`

1. 确保已安装 Java 环境（JDK 8 或更高版本）。
2. 将项目克隆到本地：
   ```bash
   git clone <https://github.com/IceCandy041119/2048>
   cd 2048
   ```
## 操作说明
- 方向键：控制数字移动。
- 空格键：暂停/恢复背景音乐。
- N 键：切换到下一首背景音乐。
- M 键：切换到上一首背景音乐。
- R 键：重新开始游戏。
- 鼠标点击：
    - 点击左侧按钮控制音乐播放、切换。
    - 点击右侧按钮重新开始游戏。
- 资源说明
    - 图片资源存放于 pic/ 文件夹中。
    - 背景音乐存放于 sound/ 文件夹中。
    - 无用的音频文件存放于 rubbish/ 文件夹中（已通过 .gitignore 忽略）。
- 注意事项
    - 确保 pic/ 和 sound/ 文件夹中的资源文件完整，否则可能导致程序运行异常。
    - 如果需要添加新的背景音乐，请将音频文件放入 sound/ 文件夹中。