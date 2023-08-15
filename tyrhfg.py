def lower(string_input):
    """
    大写转小写
    :param string_input:
    :return:
    """
    return string_input.lower()

def remove_(string_input):
    """
    去除空格
    :param string_input:
    :return:
    """
    return string_input.replace("-", " ")


def strip(string_input):
    """
    去除回车字符
    :param string_input:
    :return:
    """

    return string_input.strip("\n")
