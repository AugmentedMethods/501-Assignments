#include "stdafx.h"
#include "FileReader.h"
#include <iostream>
#include <stdio.h>
#include <math.h>
#include <fstream>
#include <stdlib.h>
#include <malloc.h>
#include <string>

FileReader::FileReader(void)
{
}


FileReader::~FileReader(void)
{
}

//GLOBALS
char *format;
int chunkSize;
char *chunkID;
char *subChunk1ID;

short int blockAlign;
short int bitsPerSample;
short int audioFormat;
short int numChannels;
int sampleRate;
char *subChunk2ID;
int dataSize;
short* fileData;
int subChunk1Size;
int byteRate;

float minRead = 0;
float maxRead = 0;

void writeWaveFile(char *fileName, int numSamples, float *signal)
{
        std::ofstream outFile( fileName, std::ios::out |std:: ios::binary);

        /*  Calculate the total number of bytes for the data chunk  */
        int chunkSize = numChannels * numSamples * (bitsPerSample / 8);

        chunkID = "RIFF";
        outFile.write( chunkID, 4);

        outFile.write( (char*) &chunkSize, 4);

        format = "WAVE";
        outFile.write( format, 4);

        outFile.write( subChunk1ID, 4);

        subChunk1Size = 16;
        outFile.write( (char*) &subChunk1Size, 4);

        outFile.write( (char*) &audioFormat, 2);

        outFile.write( (char*) &numChannels, 2);

        outFile.write( (char*) &sampleRate, 4);

        outFile.write( (char*) &byteRate, 4);

        outFile.write( (char*) &blockAlign, 2);

        outFile.write( (char*) &bitsPerSample, 2);

        outFile.write( subChunk2ID, 4);

        //Data size
        dataSize = numSamples * 2;
        outFile.write( (char*)&dataSize, 4);

        short int val;
        for(int i = 0; i < numSamples; i++)
        {
                val = (short int)(signal[i] * (pow(2,15) - 1));
                outFile.write((char*)&val, 2);
        }
        outFile.close();
}


float* readData(char *fileName, float *signal, int *Thesize)
{
        std::ifstream inFile( fileName, std::ios::in | std::ios::binary);

        inFile.seekg(std::ios::beg);

        chunkID = new char[4];
        inFile.read( chunkID, 4);

        inFile.read( (char*) &chunkSize, 4);

        format = new char[4];
        inFile.read( format, 4);

        subChunk1ID = new char[4];
        inFile.read( subChunk1ID, 4);

        inFile.read( (char*) &subChunk1Size, 4);

        inFile.read( (char*) &audioFormat, 2);

        inFile.read( (char*) &numChannels, 2);


        inFile.read( (char*) &sampleRate, 4);

        inFile.read( (char*) &byteRate, 4);

        inFile.read( (char*) &blockAlign, 2);

        inFile.read( (char*) &bitsPerSample, 2);

        if(subChunk1Size == 18)
        {
                char *garbage;
                garbage = new char[2];
                inFile.read( garbage, 2);
        }

        subChunk2ID = new char[4];
        inFile.read( subChunk2ID, 4);

        //DataSize
        inFile.read( (char*)&dataSize, 4);

        //GetData
        *Thesize = dataSize / 2;
        int size = dataSize / 2;
        fileData = new short[size];

        for(int j = 0 ; j < size; j++)
        {
                inFile.read((char*) &fileData[j], 2);
        }

        //ProduceSignal
        short val;
        signal = new float[size];
        for(int i = 0; i < size; i++)
        {
                val = fileData[i];
                signal[i] = (val * 1.0) / (pow(2,15) - 1);
                if(signal[i] < -1.0)
                        signal[i] = -1.0;

        }
        inFile.close();

        return signal;
}
