import cv2
import numpy as np
from random import randint, uniform
import os

def overlay_images(background_path, defect_path, output_path):
    # Load background and defect images
    background = cv2.imread(background_path)
    defect = cv2.imread(defect_path, cv2.IMREAD_UNCHANGED)
    height, width = background.shape[:2]
    
    # Randomly adjust the size of the defect image
    scale_factor = uniform(0.5, 1.5)
    defect_resized = cv2.resize(defect, None, fx=scale_factor, fy=scale_factor)
    
    # Randomly rotate the defect image
    angle = randint(0, 360)
    rows, cols, _ = defect_resized.shape
    rotation_matrix = cv2.getRotationMatrix2D((cols / 2, rows / 2), angle, 1)
    defect_rotated = cv2.warpAffine(defect_resized, rotation_matrix, (cols, rows))

    # Randomly adjust the brightness of the defect image
    brightness_factor = uniform(0.8, 1.0)
    defect_brightened = np.multiply(defect_rotated, brightness_factor).astype(np.uint8)
    defect_h, defect_w = defect_brightened.shape[:2]
    
    # Randomly choose a position to overlay the defect image on the background
    x_offset = randint(0, background.shape[0] - defect_brightened.shape[0])
    y_offset = randint(0, background.shape[1] - defect_brightened.shape[1])
    
    _, mask = cv2.threshold(defect_brightened[:,:,3], 1, 255, cv2.THRESH_BINARY)
    mask_inv = cv2.bitwise_not(mask)
    
    #
    defect_brightened = cv2.cvtColor(defect_brightened, cv2.COLOR_BGRA2BGR)
    background_roi = background[x_offset: x_offset + defect_h, y_offset: y_offset + defect_w]
    
    masked_defect = cv2.bitwise_and(defect_brightened, defect_brightened, mask=mask)
    masked_background = cv2.bitwise_and(background_roi, background_roi, mask=mask_inv)
    added = masked_defect + masked_background
    
    # 합성 위치 주위 평균 색 추출
    background_offset = background[x_offset - 50: x_offset + 50, y_offset-50:y_offset+50]
    cv2.imshow("background_offset", cv2.resize(background_offset, None, fx=1.0, fy=1.0))
    print(f"background_offset:{background_offset.shape}")
    average_color = np.mean(background_offset, axis=(0, 1)).astype(int)
    fill_color = tuple(average_color)
    
    # 어두운 부분에 평균 색 채우기
    mask_one_values = added[:, :, 0] <= 50
    print(added[mask_one_values])
    added[mask_one_values] = fill_color
    
    
    #
    background[x_offset: x_offset + defect_h, y_offset: y_offset + defect_w] = added
    
    # Save the result
    #cv2.imwrite(output_path, background)

    #cv2.imshow("mask", cv2.resize(mask, None, fx=3.0, fy=3.0))
    #cv2.imshow("mask_inv", cv2.resize(mask_inv, None, fx=3.0, fy=3.0))
   
    cv2.imshow("added", cv2.resize(added, None, fx=2.0, fy=2.0))
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    cv2.imshow("Image", cv2.resize(background, (width // 4, height // 4)))
    cv2.waitKey(0)
    cv2.destroyAllWindows()


directory = os.path.dirname(os.path.realpath(__file__)) 
directory = os.path.join(directory, "images")
bg_path = os.path.join(directory, "bg", "bg.png")
defect_path = os.path.join(directory, "defect", "hole12.png")
output_path = os.path.join(directory, "output", "output.png")

#for i in range(0,10):
overlay_images(bg_path, defect_path, output_path)