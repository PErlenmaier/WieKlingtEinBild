#include "Processing.h"
#define DEBUG
#ifdef DEBUG
#include <iostream>
#endif

#ifdef DEBUG
void printProgress(int x, int xMax)
{
	if (!(x % 8))
		std::cout << "Progress:\t" << std::to_string(((static_cast<double>(x) / xMax) * 100)) << "%\r";
}
#endif

/*AdditionalSettings
 *Settings1: Maximale Frequenz
 *Settings2: Aktivierungsschwelle f�r Pixel
 *Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
 */
void Processing::Processing::lrScan(const AdditionalSettings& additionalSettings)
{
	const auto width = image->getWidth();
	const auto height = image->getHeight();
	const auto freqPerRow = static_cast<const int>(round(additionalSettings.settings1 / height));
	auto volume = 0.0;

	for (auto x = 0; x < width; ++x)
	{
		//Frequenz fuer jeden Pixel berechnen
		for (auto y = 0; y < height; ++y)
		{
			const auto& pixel = image->getPixel(y, x);

			//pixel r,g,b gewichten fuer Amplitude, entweder inverses oder nicht
			if (additionalSettings.settings4 != 0)
			{
				const auto invRed = (255 - pixel.r);
				const auto invGreen = (255 - pixel.g);
				const auto invBlue = (255 - pixel.b);
				volume = (static_cast<double>(invRed + invGreen + invBlue)) / (3.0);
			}
			else
			{
				volume = (static_cast<double>(pixel.r + pixel.g + pixel.b)) / (3.0);
			}

			//Aktivierungsschwelle
			if (volume > additionalSettings.settings2)
				volume = 0;
			else
				volume /= height;

			//Frequenz ist abhaengig von der derzeitigen Zeile
			const auto frequency = y * freqPerRow;
			//Frequenz zum Soundbuffer hinzuf�gen
			sound->addFrequency(volume, frequency);
		}
		//Die Daten aus dem Soundbuffer in die .wav Datei schreiben
		for (auto& i : sound->getBuffer())
			wave->writeSamples(static_cast<uint16_t>(i));

		//Buffer leeren
		sound->resetBuffer();

		//Statusausgabe
#ifdef DEBUG
		printProgress(x, width);
#endif
	}
}


/*AdditionalSettings
*Settings1: Maximale Frequenz
*Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
*/
void Processing::Processing::lrScan_no_threshold(const AdditionalSettings& additionalSettings)
{
	const auto width = image->getWidth();
	const auto height = image->getHeight();
	const auto freqPerRow = static_cast<const int>(round(additionalSettings.settings1 / height));
	auto volume = 0.0;
	for (auto x = 0; x < width; ++x)
	{
		//Frequenz fuer jeden Pixel berechnen
		for (auto y = 0; y < height; ++y)
		{
			const auto& pixel = image->getPixel(y, x);

			//pixel r,g,b gewichten fuer Amplitude, entweder inverses oder nicht
			if (additionalSettings.settings4 != 0)
			{
				const auto invRed = (255 - pixel.r);
				const auto invGreen = (255 - pixel.g);
				const auto invBlue = (255 - pixel.b);
				volume = (static_cast<double>(invRed + invGreen + invBlue)) / (3.0 * height);
			}
			else
			{
				volume = (static_cast<double>(pixel.r + pixel.g + pixel.b)) / (3.0 * height);
			}

			//Frequenz ist abhaengig von der derzeitigen Zeile
			const auto frequency = y * freqPerRow;
			//Frequenz zum Soundbuffer hinzuf�gen
			sound->addFrequency(volume, frequency);
		}
		//Die Daten aus dem Soundbuffer in die .wav Datei schreiben
		for (auto& i : sound->getBuffer())
			wave->writeSamples(static_cast<uint16_t>(i));

		//Buffer leeren
		sound->resetBuffer();

		//Statusausgabe
#ifdef DEBUG
		printProgress(x, width);
#endif
	}
}

/*AdditionalSettings
*Settings1: Maximale Frequenz
*Settings2: Aktivierungsschwelle f�r Pixel
*Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
*/
void Processing::Processing::udScan(const AdditionalSettings& additionalSettings)
{
	const auto width = image->getWidth();
	const auto height = image->getHeight();
	const auto freqPerRow = static_cast<const int>(round(additionalSettings.settings1 / height));
	auto volume = 0.0;

	for (auto y = 0; y < height; ++y)
	{
		//Frequenz fuer jeden Pixel berechnen
		for (auto x = 0; x < width; ++x)
		{
			const auto& pixel = image->getPixel(y, x);

			//pixel r,g,b gewichten fuer Amplitude, entweder inverses oder nicht
			if (additionalSettings.settings4 != 0)
			{
				const auto invRed = (255 - pixel.r);
				const auto invGreen = (255 - pixel.g);
				const auto invBlue = (255 - pixel.b);
				volume = (static_cast<double>(invRed + invGreen + invBlue)) / 3.0;
			}
			else
			{
				volume = (static_cast<double>(pixel.r + pixel.g + pixel.b)) / 3.0;
			}
			//Aktivierungsschwelle
			if (volume > additionalSettings.settings2)
				volume = 0;
			else
				volume /= height;

			//Frequenz ist abhaengig von der derzeitigen Zeile
			const auto frequency = y * freqPerRow;
			//Frequenz zum Soundbuffer hinzuf�gen
			sound->addFrequency(volume, frequency);
		}
		//Die Daten aus dem Soundbuffer in die .wav Datei schreiben
		for (auto& i : sound->getBuffer())
			wave->writeSamples(static_cast<uint16_t>(i));

		//Buffer leeren
		sound->resetBuffer();

		//Statusausgabe
#ifdef DEBUG
		printProgress(y, height);
#endif
	}
}

/*AdditionalSettings
*Settings1: Maximale Frequenz
*Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
*/
void Processing::Processing::udScan_no_threshold(const AdditionalSettings& additionalSettings)
{
	const auto width = image->getWidth();
	const auto height = image->getHeight();
	const auto freqPerRow = static_cast<const int>(round(additionalSettings.settings1 / height));
	auto volume = 0.0;

	for (auto y = 0; y < height; ++y)
	{
		//Frequenz fuer jeden Pixel berechnen
		for (auto x = 0; x < width; ++x)
		{
			const Pixel& pixel = image->getPixel(y, x);

			//pixel r,g,b gewichten fuer Amplitude, entweder inverses oder nicht
			if (additionalSettings.settings4 != 0)
			{
				const auto invRed = (255 - pixel.r);
				const auto invGreen = (255 - pixel.g);
				const auto invBlue = (255 - pixel.b);
				volume = (static_cast<double>(invRed + invGreen + invBlue)) / (3.0 * height);
			}
			else
			{
				volume = (static_cast<double>(pixel.r + pixel.g + pixel.b)) / (3.0 * height);
			}

			//Frequenz ist abhaengig von der derzeitigen Zeile
			const auto frequency = y * freqPerRow;
			//Frequenz zum Soundbuffer hinzuf�gen
			sound->addFrequency(volume, frequency);
		}
		//Die Daten aus dem Soundbuffer in die .wav Datei schreiben
		for (auto& i : sound->getBuffer())
			wave->writeSamples(static_cast<uint16_t>(i));

		//Buffer leeren
		sound->resetBuffer();

		//Statusausgabe
#ifdef DEBUG
		printProgress(y, height);
#endif
	}
}


/*AdditionalSettings
*Settings2: Tondauer abhaengig von dem Blauanteil
*Settings3: Anzahl der Triplets die man pro Frequenz �berspringen soll
*Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
*/
void Processing::Processing::triplet(const AdditionalSettings& additionalSettings)
{
	const auto width = image->getWidth();
	const auto height = image->getHeight();

	auto currentPos = 0;
	uint8_t red = 0, green = 0, blue = 0;


	for (int x = 1; x < width * height; x += 3 * additionalSettings.settings3)
	{
		const auto& pixel = image->getPixel(currentPos / width, currentPos % width);
		currentPos += 3 * additionalSettings.settings3;
		currentPos %= width * height;

		if(additionalSettings.settings4 != 0)
		{
			blue = 255 - pixel.b;
		}
		else
		{
			blue = pixel.b;
		}

		//Ton erzeugen. Tondauer abhaengig von Blauanteil
		for (auto i = 0; i < blue; i += additionalSettings.settings2)
		{
			if (additionalSettings.settings4 != 0)
			{
				red = 255 - pixel.r;
				green = 255 - pixel.g;
			}
			else
			{
				red = pixel.r;
				green = 255 - pixel.g;
			}

			sound->addFrequency(red, green);
			for (auto& u : sound->getBuffer())
				wave->writeSamples(static_cast<uint16_t>(u));
			sound->resetBuffer();
		}

#ifdef DEBUG
		printProgress(x, width);
#endif
	}
}


/*AdditionalSettings
*Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
*/
void Processing::Processing::triplet_jmp(const AdditionalSettings& additionalSettings)
{
	const auto width = image->getWidth();
	const auto height = image->getHeight();
	const auto freqRed = additionalSettings.settings1 / 255.0;

	auto currentPos = 0;
	uint8_t red = 0, green = 0, blue = 0;


	for (auto x = 1; x < width; ++x)
	{
		const auto& pixel = image->getPixel(currentPos / width, currentPos % width);

		if (additionalSettings.settings4 != 0)
		{
			red = 255 - pixel.r;
			green = 255 - pixel.g;
			blue = 255 - pixel.b;
		}
		else
		{
			red = pixel.r;
			green = pixel.g;
			blue = pixel.b;
		}
		//frequenz hinzufuegen
		sound->addFrequency(red, green);

		//sich um x  bewegen
		currentPos += (currentPos + blue + 1) % (width*height);
		//alles in die datei schreiben
		for (auto& i : sound->getBuffer())
			wave->writeSamples(static_cast<uint16_t>(i));

		sound->resetBuffer();
#ifdef DEBUG
		printProgress(x, width);
#endif
	}
}

/*AdditionalSettings
*Settings1: Max Frequenz
*Settings2: Tondauer abhaengig von dem Blauanteil
*Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
*/
void Processing::Processing::ud_lr_scan(const AdditionalSettings& additionalSettings)
{
	const auto width = image->getWidth();
	const auto height = image->getHeight();
	const auto maxColorWidth = width * 255;
	const auto maxColorHeight = height * 255;

	auto xLR = 0, yUD = 0;
	for (auto done = false; done != true;)
	{
		done = true;

		//Von oben nach unten
		int red = 0, green = 0, blueAll = 0;

		//Lambda Funktionen zum aufaddieren der Werte
		const auto addRGBInverted = [&](const auto y, const auto x)
		{
			const auto& pixel = image->getPixel(y, x);
			red += 255 - pixel.r;
			green += 255 - pixel.g;
			blueAll += 255 - pixel.b;
		};

		const auto addRGB = [&](const auto y, const auto x)
		{
			const auto& pixel = image->getPixel(y, x);
			red += pixel.r;
			green += pixel.g;
			blueAll += pixel.b;
		};

		//Derzeitige Zeile durchscannen
		if (additionalSettings.settings4 != 0)
		{
			for (auto xUD = 0; xUD < width; ++xUD)
				addRGBInverted(yUD, xUD);
		}
		else
		{
			for (auto xUD = 0; xUD < width; ++xUD)
			{
				addRGB(yUD, xUD);
			}
		}
		//Rotanteil -> Frequenz ; Gruenanteil -> Amplitude
		const auto frequencyUD = (static_cast<double>(red) * additionalSettings.settings1) / maxColorWidth;
		const auto volumeUD = (static_cast<double>(green)) / (maxColorWidth * 2.0);

		//Derzeitige Spalte durchscannen
		red = 0, green = 0;
		if (additionalSettings.settings4 != 0)
		{
			for (auto yLR = 0; yLR < height; ++yLR)
			{
				addRGBInverted(yLR, xLR);
			}
		}
		else
		{
			for (auto yLR = 0; yLR < height; ++yLR)
			{
				addRGB(yLR, xLR);
			}
		}
		//Rotanteil -> Frequenz ; Gruenanteil -> Amplitude
		const auto frequencyLR = (static_cast<double>(red) * additionalSettings.settings1) / maxColorWidth;
		const auto volumeLR = (static_cast<double>(green)) / (maxColorWidth * 2.0);

		//Laenge des Tones abhaengig vom gemeinsamen Blauanteil
		const auto duration = (static_cast<double>(blueAll) / (maxColorWidth + maxColorHeight)) * additionalSettings.
			settings2;

		//Toene erzeugen und in Datei schreiben
		for (auto i = 0; i < duration; ++i)
		{
			sound->addFrequency(volumeLR, frequencyLR);
			sound->addFrequency(volumeUD, frequencyUD);

			for (auto& u : sound->getBuffer())
				wave->writeSamples(static_cast<uint16_t>(u));

			sound->resetBuffer();
		}

		//Laufvariablen inkrementieren
		if (xLR < (width - 1))
		{
			++xLR;
			done = false;
		}
		if (yUD < (height - 1))
		{
			++yUD;
			done = false;
		}

		//Statusausgabe
#ifdef DEBUG
		printProgress(xLR * yUD, height * width);
#endif
	}
}

void Processing::Processing::start(const std::string& imageFile, const std::string& outputFile,
                                   ProcessingAlgorithm algorithm,
                                   const ProcessingSettings& settings, const AdditionalSettings& additionalSettings)
{
	//Objekte erzeugen und initialisieren
	image = std::make_unique<Image>(imageFile);
	wave = std::make_unique<Wave>(outputFile, settings.samplingFrequency);
	sound = std::make_unique<Sound>(settings.samplingFrequency, settings.audioVolume, settings.samples);

	switch (algorithm)
	{
		//Von Links nach Rechts Bild abscannen, entweder mit mindest Aktivierungsschwelle damit Ton f�r den Pixel erzeugt wird oder ohne
	case ProcessingAlgorithm::LR_SCAN:
		lrScan(additionalSettings);
		break;
	case ProcessingAlgorithm::LR_SCAN_NO_THRESHOLD:
		lrScan_no_threshold(additionalSettings);
		break;

		//Bild von oben nach unten abscannen, sonst identisch mit LR_SCAN
	case ProcessingAlgorithm::UD_SCAN: //up down scan
		udScan(additionalSettings);
		break;
	case ProcessingAlgorithm::UD_SCAN_NO_THRESHOLD:
		udScan_no_threshold(additionalSettings);
		break;

		//Jeweils immer 3 Triplets einscannen, entweder gibt Blau Tondauer oder Offset zum n�chsten Triplet an
	case ProcessingAlgorithm::TRIPLET:
		triplet(additionalSettings);
		break;
	case ProcessingAlgorithm::TRIPLET_JMP:
		triplet_jmp(additionalSettings);
		break;

	case ProcessingAlgorithm::UD_LR_SCAN:
		ud_lr_scan(additionalSettings);
		break;

		//TODO Mehr Algorithmen implementieren
	default:
		break;
	}


	//Header abschliessen
	wave->finishHeader();
	wave->closeFile();

	//Alle Objekte explizit abbauen
	wave.reset();
	image.reset();
	sound.reset();
}
