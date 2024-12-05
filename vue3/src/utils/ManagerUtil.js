const isLegal = (str) => {
  if (typeof str !== 'string') {
    return false
  }
  const illegalChars = ["$", "#", "%", "^", "&", "!", "@"];
  for (let c of str) {
    if (illegalChars.includes(c)) {
      return false;
    }
  }
  return true;
}

export default isLegal