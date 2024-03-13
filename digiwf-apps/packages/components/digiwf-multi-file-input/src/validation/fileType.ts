import mime from "mime";

export const validateFileType = (fileName: string, acceptedString: string | undefined): string | undefined => {
  if (!acceptedString || acceptedString.trim().length === 0) {
    return
  }

  const accept: string[] = acceptedString.split(",");
  if (accept.length > 0) {
    const mimeType = getMimeType(fileName)
    if (!accept.includes(mimeType)) {
      return `Ungültiger Dateityp. Validate Dateitypen sind ${accept.join(", ")}`;
    }
  }
}

export const getMimeType = (filename: string) => {
  const mimetype = mime.getType(filename);
  return mimetype ? mimetype : "text/plain";
}
