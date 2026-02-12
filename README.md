下面是根据你提供的 GitHub 仓库 **paditianxiu/orientation-faker**（一款屏幕方向管理器，类似于 Rotation）生成的 Markdown 文档草稿：

---

# orientation-faker

一款屏幕方向管理器，类似于 Rotation。

## 🧩 项目图片

![项目图片](./images/A9B763EA2A608241E166DFDB1394E6EC.jpg)

## 🧠 项目简介

`orientation-faker` 是一个用于 Android 平台的屏幕方向控制工具。它允许你在应用运行时强制固定屏幕方向，或根据传感器旋转屏幕，而不受当前显示应用默认方向的限制。

## 🚀 主要功能

* 支持多种屏幕方向设置，包括固定方向和基于传感器旋转。
* 可从通知区域快速切换方向。
* 支持为特定应用设置固定方向并自动切换。

## 📌 支持的屏幕方向模式

以下是一些典型的屏幕旋转模式：

| 模式                                            | 描述               |
| --------------------------------------------- | ---------------- |
| unspecified                                   | 不指定方向，使用当前应用默认方向 |
| portrait                                      | 纵向固定             |
| landscape                                     | 横向固定             |
| rev portrait                                  | 反向纵向             |
| rev landscape                                 | 反向横向             |
| full sensor                                   | 全部方向跟随传感器        |
| sensor portrait                               | 纵向且按传感器翻转        |
| sensor landscape                              | 横向且按传感器翻转        |
| lie left                                      | 左侧朝下             |
| lie right                                     | 右侧朝下             |
| headstand                                     | 头朝下              |
| forward                                       | 正向旋转             |
| reverse                                       | 反向旋转             |
| *(以上说明参考通用 OrientationFaker 行为)* |                  |

## 📦 安装

此仓库目前尚无发行版发布（没有 Releases）。可以直接克隆源码并使用 Android Studio 构建。

```bash
git clone https://github.com/paditianxiu/orientation-faker.git
```

然后用 Android Studio 打开并运行项目。

## 📁 项目结构

```
orientation-faker/
├── app/                       # 主应用模块
├── gradle/                    # Gradle 配置
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── .gitignore
```

## 📱 使用说明

1. 安装并运行应用。
2. 从系统通知栏打开方向控制菜单。
3. 选择你希望锁定或旋转的屏幕方向。
4. 当你为应用绑定了特定方向时，打开该应用会自动切换方向。

⚠️ 注意：强制旋转屏幕可能导致某些应用 UI 显示异常或崩溃，请谨慎使用。

## 📄 许可证

本项目采用 **MIT 许可证**。

## 📌 作者

`paditianxiu`（GitHub 用户）创建维护。

---