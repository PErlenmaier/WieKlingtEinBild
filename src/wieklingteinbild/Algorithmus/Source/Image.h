#pragma once
#include <fstream>
#include <string>
#include <vector>


/**
 * \brief Verwaltungsinformationen zu Bitmap Dateien
 */
namespace bitmap
{
	/**
	* \brief Dateiheader fuer .bmp
	*/
	typedef struct
	{
		uint16_t bfType;					//ASCII Zeichen "BM" 
		uint32_t bfFileSize;				//Groesse der .bmp Datei in bytes
		uint16_t bfRes1;					//0
		uint16_t bfRes2;					//0
		uint32_t bfStartAdress;				//Offset zu den Bilddaten von Beginn der Datei
	} BitmapFileHeader;


	/**
	* \brief Bitmapheader. Enthaelt Informationen ueber die Eigenschaften des Bildes
	*/
	typedef struct
	{
		uint32_t biSize;					//Groesse dieses Headers in Byte
		uint32_t biWidth;					//Breite des Bildes in Pixel
		uint32_t biHeight;					//Hoehe des Bildes in Pixel
		uint16_t biColorPlanes;				//In .bmp nicht verwendet
		uint16_t biBitsPerPixel;			//Farbtiefe in bpp (8)
		uint32_t biCompression;				//-
		uint32_t biImageSize;				//Groesse der Bilddaten 
		uint32_t biHorizontalResolution;	//-
		uint32_t biVerticalResolution;		//-
		uint32_t biColorsPerColorPalette;	//-
		uint32_t biImportantColorsUsed;		//-
	} BitmapInfoHeader;
}

/**
 * \brief Pixel 8 Bit RGB Werte 
 */
typedef struct
{
	uint8_t r;
	uint8_t g;
	uint8_t b;
} Pixel;


/**
 * \brief Klasse zum oeffnen und auslesen von Bitmap Bilddateien.
 */
class Image
{
private:

	/**
	 * \brief Informationen ueber die Datei (Datentyp,Bytes,Startadresse der Bilddaten..)
	 */
	bitmap::BitmapFileHeader bitmapFileHeader;

	/**
	 * \brief Informationen ueber das Bild (Breite, Hoehe, Farben..)
	 */
	bitmap::BitmapInfoHeader bitmapInfoHeader;

	/**
	 * \brief 2D Vector mit den Pixeldaten des Bildes 
	 */
	std::vector<std::vector<Pixel>> bitmapData;

	/**
	 * \brief Header der .bmp Datei auslesen, um Verwaltungsinformationen wie z.B. Hoehe und Breite zu erhalten
	 */
	void readHeader();

	/**
	 * \brief Bilddaten aus der .bmp Datei auslesen und in diesem Objekt speichern
	 */
	void readImageData();

	/**
	 * \brief Inputfilestream fuer die zu oeffnende Bilddatei
	 */
	std::ifstream file;
public:
	/**
	 * \brief Standardkonstruktor
	 */
	Image() = delete;

	/**
	 * \brief Standard Movekonstruktor
	 * \param other: Image rvalue 
	 */
	Image(Image&& other) = default;

	/**
	 * \brief Konstruktor. Oeffnet .bmp Datei mit dem angegebenen Namen
	 * \param fileName: Name der .bmp Datei als String
	 */
	Image(std::string fileName);

	/**
	 * \brief Zuweisungen fuer Image nicht verfuegbar
	 */
	Image& operator=(const Image& other) = delete;

	/**
	 * \brief Zuweisungen fuer Image nicht verfuegbar
	 */
	Image& operator=(Image&& other) = delete;

	/**
	 * \brief Pixel an der Stelle (y,x) auslesen
	 * \param y: Koordinate Y
	 * \param x  Koordinate X
	 * \return Referenz auf ein konstanten Pixel der sich an Stelle (y,x) befindet.
	 */
	const Pixel& getPixel(int y, int x) const
	{
		return bitmapData[y][x];
	}

	/**
	 * \brief Hoehe des Bildes erhalten
	 * \return Hoehe als uint32_t
	 */
	uint32_t getHeight() const
	{
		return bitmapInfoHeader.biHeight;
	}

	/**
	 * \brief Breite des Bildes erhalten
	 * \return Breite als uint32_t
	 */
	uint32_t getWidth() const
	{
		return bitmapInfoHeader.biWidth;
	}

	~Image();
};

