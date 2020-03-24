#Now working on python

steps to follow:

- Put inkml files into a folder (predered name: CROHME)
- Execute the main_converter script with python3
- Specify the path from the CROHME dataset
- The results should appear in a folder called converted_expressions

main_converter script by @QApolo
inkml2img script took from ![](https://github.com/RobinXL/inkml2img/)
-----------------------------------------------------------
# Original usage from repo:
## Convert inkml to image
1. Convert inkml to image

use it by direct import

#### example in jupyter notebook:
```python
import inkml2img, glob, os
inkml2img.inkml2img('2013ALL_inkml_data/200923-1556-49.inkml','./2013ALL_inkml_data_image/200923-1556-49.png')

```
#### result:
![](https://github.com/RobinXL/inkml2img/blob/master/200923-1556-49.png)

2. Convert latex to image
uses matplotlib to plot latex and save to image
see inkml2img.latex2img function


Inkml Data can be found in CROHME competition open dataset

*inkml trace extraction credits to rep: https://github.com/ThomasLech/CROHME_extractor
