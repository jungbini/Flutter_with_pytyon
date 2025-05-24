# android/app/src/main/python/my_script.py
import numpy as np

def greet(name):
    return f"Hello, {name} from Python!"

def add_arrays(arr1, arr2):
    np_arr1 = np.array(arr1)
    np_arr2 = np.array(arr2)
    result = np_arr1 + np_arr2
    return result.tolist() # Java/Kotlin으로 전달하기 쉬운 형태로 변환