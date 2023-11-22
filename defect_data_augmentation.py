import cv2
import numpy as np
from random import randint, uniform
import os

def overlay_images(background_path, defect_path, output_path):
    # Load background and defect images
    background = cv2.imread(background_path)
    defect = cv2.imread(defect_path, cv2.IMREAD_UNCHANGED)

    # Randomly adjust the size of the defect image
    scale_factor = uniform(0.5, 1.5)
    defect_resized = cv2.resize(defect, None, fx=scale_factor, fy=scale_factor)
    
    # Randomly rotate the defect image
    angle = randint(0, 360)
    rows, cols, _ = defect_resized.shape
    rotation_matrix = cv2.getRotationMatrix2D((cols / 2, rows / 2), angle, 1)
    defect_rotated = cv2.warpAffine(defect_resized, rotation_matrix, (cols, rows))

    # Randomly adjust the brightness of the defect image
    brightness_factor = uniform(0.8, 1.05)
    defect_brightened = np.multiply(defect_rotated, brightness_factor).astype(np.uint8)

    cv2.imshow("Image", defect_brightened)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    # Randomly choose a position to overlay the defect image on the background
    x_offset = randint(0, background.shape[1] - defect_brightened.shape[1])
    y_offset = randint(0, background.shape[0] - defect_brightened.shape[0])

    # Create a mask for the defect image
    mask = defect_brightened[:, :, 3] / 255.0
    background[y_offset:y_offset + defect_brightened.shape[0], x_offset:x_offset + defect_brightened.shape[1]] = cv2.addWeighted(
        background[y_offset:y_offset + defect_brightened.shape[0], x_offset:x_offset + defect_brightened.shape[1]],
        1 - mask,
        defect_brightened[:, :, :3],
        mask,
        0
    )
    # Save the result
    cv2.imwrite(output_path, background)

directory = os.path.dirname(os.path.realpath(__file__)) 
directory = os.path.join(directory, "images")
print(directory)
# Example usage
bg_path = os.path.join(directory, "bg", "bg.png")
defect_path = os.path.join(directory, "defect", "hole12.png")
output_path = os.path.join(directory, "output", "output.png")

overlay_images(bg_path, defect_path, output_path)