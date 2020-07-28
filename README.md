# System for Handwritten Mathematical expressions  extracted from smarthphone pictures and translation to LaTeX
Original Title:  "Prototipo de sistema para el reconocimiento de texto en imágenes y su traducción a LaTex".
## Description
```
This repo contains the capstone project developed during 1 year until June 2020.
The project is a system for Mathematical expressions recognition given an image taken from a smartphone.
```

![Architecture](https://github.com/IanMendozaJaimes/SuperTT/blob/master/media_markdown/exmaple.PNG?raw=true)

#### Team Integrants: 
 * [Barrera Pérez Carlos Tonatihu](https://github.com/tonabarrera)
 * [García Medina Juan Carlos](https://github.com/QApolo)
 * [Mendoza Jaimes Ian](https://github.com/IanMendozaJaimes)
#### Director: 
 * [Cortés Galicia Jorge](https://ieeexplore.ieee.org/author/37086682631)


## State of the art
* [Mathpix](https://mathpix.com/) 
* MyScript Nebo
* SESHAT

## Papers and theory Behind:

(09 abril 2019)
* Watch, attend and parse: An end-to-end neural network based approach to handwritten mathematical expression recognition
[PDF Link]( http://home.ustc.edu.cn/~xysszjs/paper/PR2017.pdf)
[local Link](https://drive.google.com/open?id=1q7J-Fs8jnWT0yXSlXNXTd48s-dBytrxv)
* CHROME dataset desctiption.
[link](http://www.iapr-tc11.org/mediawiki/index.php/CROHME:_Competition_on_Recognition_of_Online_Handwritten_Mathematical_Expressions)

* Image-to-Markup Generation with Coarse-to-Fine Attention
[PDF link](https://arxiv.org/pdf/1609.04938.pdf)
[Local link](https://drive.google.com/open?id=1NWzY9_ReEqHK6YaZpkt9tPM9srUDunNW)
* Introductory paper to the technique used in the above papers (newer methods)
[Paper](https://towardsdatascience.com/build-a-handwritten-text-recognition-system-using-tensorflow-2326a3487cd5)

## Specific Modules
All of the modules are stored within this same repo well organized inside folders.
*  [**Image analysis module**](https://github.com/IanMendozaJaimes/SuperTT/tree/master/ImageAnalysis_Module/ImagePreprocessor) This process the taken picture from the smartphone and transform it to be as close as possible to the images from the Dataset.

* [**LaTeX Translation module**](https://github.com/IanMendozaJaimes/SuperTT/tree/master/nn) Deep Learning module developed using TensorFlow 2.0 that takes as input the image previously processed by the Image analysis module.

* [**LaTeX tokenizer**](https://github.com/IanMendozaJaimes/SuperTT/tree/master/MexpTokenizer) Module developed using Lex in python that takes a LaTeX sequence and returns a numeric sequence to be loaded later as a Tensor in TensorFlow.

*  [**Android app**](https://github.com/IanMendozaJaimes/SuperTT/tree/master/SuperTTApp) for taking pictures containing handwritten mathematical expressions

* [**Web server**](https://github.com/IanMendozaJaimes/SuperTT/tree/master/supertt) Server that handles the above described modules communicating the android app with the database through a REST API.

* **Users management module** This module allows to have in the UI the translations organized in projects giving the user the ability to login anywhere and see his previous projects. 