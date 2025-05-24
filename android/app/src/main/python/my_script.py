# android/app/src/main/python/my_script.py
import numpy as np
import qrcode
import os

def greet(app_files_dir):
    qr_img = qrcode.make('www.naver.com')

    # 안전한 내부 저장소 경로에 저장
    save_path = os.path.join(app_files_dir, 'qr.png')
    qr_img.save(save_path)
    return 'success'

def add_arrays(arr1, arr2):
    np_arr1 = np.array(arr1)
    np_arr2 = np.array(arr2)
    result = np_arr1 + np_arr2
    return result.tolist() # Java/Kotlin으로 전달하기 쉬운 형태로 변환